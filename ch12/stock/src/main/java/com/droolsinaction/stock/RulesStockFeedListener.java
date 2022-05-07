package com.droolsinaction.stock;

import org.kie.api.runtime.rule.EntryPoint;

public class RulesStockFeedListener implements StockFeedListener {

	private EntryPoint entryPoint;

	public RulesStockFeedListener(EntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	@Override
	public void onTick(Tick tick) {
		entryPoint.insert(tick);
	}

}
