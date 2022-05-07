package com.droolsinaction.stock;

import java.io.IOException;
import java.math.BigDecimal;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

public class StockFeedSimulator {

	private final static BigDecimal buyTolerance = new BigDecimal("0.75");
	private final static BigDecimal sellTolerance = new BigDecimal("0.50");

	public static void main(String[] args) {

		KieServices kieServices = KieServices.Factory.get();
		KieContainer container = kieServices.getKieClasspathContainer();
		final KieSession session = container.newKieSession("stockKS");

		EntryPoint entryPoint = session.getEntryPoint("StockFeed");

		session.setGlobal("buyTolerance", buyTolerance);
		session.setGlobal("sellTolerance", sellTolerance);

		StockFeeder feeder = new StockFeeder("src/main/resources/instructions.txt");
		feeder.addListener(new LoggingStockFeedListener());
		feeder.addListener(new RulesStockFeedListener(entryPoint));

		new Thread(new Runnable() {

			@Override
			public void run() {
				session.fireUntilHalt();
			}
		}).start();

		try {
			feeder.start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.halt();
			session.dispose();
		}

	}

}
