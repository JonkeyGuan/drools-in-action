package com.droolsinaction.stock;

import java.text.SimpleDateFormat;

public class LoggingStockFeedListener implements StockFeedListener {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onTick(Tick tick) {
		System.out.println(
				"发生股票交易 -> 时间: " + dateFormat.format(tick.getTimestamp()) + ", 代码: " + tick.getSymbol() + ", 数量: "
						+ tick.getShares() + ", 价格: " + tick.getPrice());
	}
}
