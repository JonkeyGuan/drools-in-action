package com.droolsinaction.stock;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class StockFeeder {

	private List<StockFeedListener> listeners = new ArrayList<StockFeedListener>();
	private String instructionFile;
	private List<StockInstruction> instructions = new ArrayList<StockInstruction>();
	private Map<String, BigDecimal> stocks = Collections.synchronizedMap(new HashMap<String, BigDecimal>());
	private AtomicInteger threadsActive = new AtomicInteger(0);

	public StockFeeder(String instructionFile) {
		this.instructionFile = instructionFile;
	}

	public void addListener(StockFeedListener listener) {
		listeners.add(listener);
	}

	public void start() throws IOException {
		readInstructions();
		stocks.clear();

		for (StockInstruction i : instructions) {
			fireInstruction(i);
		}
		while (!isFinished()) {
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isFinished() {

		if (threadsActive.get() == 0) {
			return true;
		}

		return false;
	}

	private void readInstructions() throws IOException {
		instructions.clear();
		FileInputStream fis = new FileInputStream(instructionFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String line;
		int lineNumber = 0;
		try {
			while ((line = br.readLine()) != null) {
				++lineNumber;
				String[] tokens = line.split(",");
				if (tokens.length < 1)
					throw new IllegalArgumentException("Invalid format at line " + lineNumber);
				if (tokens[0].startsWith("#")) {
					// skip
				} else if ("S".equals(tokens[0])) {
					if (tokens.length != 3)
						throw new IllegalArgumentException("Invalid instruction at line " + lineNumber
								+ ". Set Price (S) must contain symbol and price");
					instructions.add(
							new StockInstruction(StockInstructionType.SET_PRICE, tokens[1], new BigDecimal(tokens[2])));
				} else if ("I".equals(tokens[0]) || "D".equals(tokens[0])) {
					if (tokens.length != 5)
						throw new IllegalArgumentException(
								"Invalid instruction at line " + lineNumber + ". Change Price (I/D) must contain "
										+ "symbol, minutes, increment, and volume range");
					instructions.add(new StockInstruction(
							"D".equals(tokens[0]) ? StockInstructionType.DECREASE_PRICE
									: StockInstructionType.INCREASE_PRICE,
							tokens[1], new BigDecimal(tokens[3]), Integer.valueOf(tokens[2]), tokens[4]));

				} else if ("P".equals(tokens[0])) {
					if (tokens.length != 2)
						throw new IllegalArgumentException("Invalid instruction at line " + lineNumber);
					instructions.add(new StockInstruction(StockInstructionType.PAUSE, Integer.valueOf(tokens[1])));
				} else
					throw new IllegalArgumentException(
							"Invalid instruction, " + tokens[0] + " received at line " + lineNumber);
			}
		} finally {
			br.close();
			fis.close();
		}
	}

	private void fireInstruction(StockInstruction instruction) {

		switch (instruction.command) {
			case SET_PRICE:
				stocks.put(instruction.symbol, instruction.amount);
				publish(new Tick(instruction.symbol, instruction.amount, 100, new Date()));
				break;
			case INCREASE_PRICE:
			case DECREASE_PRICE:
				new Thread(new Generator(instruction)).start();
				threadsActive.getAndIncrement();
				break;
			case PAUSE:
				try {
					Thread.sleep(instruction.millis);
				} catch (Exception e) {
				}
				break;
		}
	}

	private void publish(Tick tick) {
		for (StockFeedListener l : listeners) {
			l.onTick(tick);
		}
	}

	private class StockInstruction {
		StockInstructionType command;
		String symbol;
		BigDecimal amount;
		long millis;
		int lowVolume;
		int hiVolume;

		StockInstruction(StockInstructionType command, String symbol, BigDecimal amount) {
			this.command = command;
			this.symbol = symbol;
			this.amount = amount;
		}

		StockInstruction(StockInstructionType command, String symbol, BigDecimal amount, int seconds,
				String volumeRange) {
			this.command = command;
			this.symbol = symbol;
			this.amount = amount;
			this.millis = seconds * 1000;
			String[] tokens = volumeRange.split("-");
			if (tokens.length != 2)
				throw new IllegalArgumentException("Volume not specified correctly, must be separated by dash");
			this.lowVolume = Integer.valueOf(tokens[0]);
			this.hiVolume = Integer.valueOf(tokens[1]);
		}

		StockInstruction(StockInstructionType command, int seconds) {
			this.command = command;
			this.millis = seconds * 1000L;
		}

	}

	private enum StockInstructionType {
		SET_PRICE, INCREASE_PRICE, DECREASE_PRICE, PAUSE
	}

	private class Generator implements Runnable {

		long stopTime;
		StockInstruction instruction;

		Generator(StockInstruction instruction) {
			this.instruction = instruction;
			stopTime = System.currentTimeMillis() + instruction.millis;
		}

		@Override
		public void run() {
			while (System.currentTimeMillis() < stopTime) {
				BigDecimal currentPrice = stocks.get(instruction.symbol);
				if (currentPrice == null)
					System.err.println("Cannot increment stock, " + instruction.symbol + ", no price set");

				BigDecimal newPrice = ((instruction.command == StockInstructionType.INCREASE_PRICE)
						? currentPrice.add(instruction.amount)
						: currentPrice.subtract(instruction.amount));
				stocks.put(instruction.symbol, newPrice);
				int volume = new Random(System.currentTimeMillis())
						.nextInt(instruction.hiVolume - instruction.lowVolume) + instruction.lowVolume;
				publish(new Tick(instruction.symbol, newPrice, volume, new Date()));
				try {
					int sleepTime = new Random(System.currentTimeMillis()).nextInt(7000) + 3000;
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			threadsActive.getAndDecrement();
		}
	}
}
