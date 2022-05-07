package com.droolsinaction.expressfeeprarate;

import java.io.Serializable;

public class ExpressFee implements Serializable {

	static final long serialVersionUID = 1L;

	private String month;
	private double amount;

	public ExpressFee() {
	}

	public ExpressFee(String month, double amount) {
		this.month = month;
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}