package com.droolsinaction.expressfeeprarate;

import java.io.Serializable;

public class Revenue implements Serializable {

	static final long serialVersionUID = 1L;

	private String month;
	private String department;
	private String channel;
	private double amount;

	private double fee;

	public Revenue() {
	}

	public Revenue(String month, String department, String channel, double amount) {
		this.month = month;
		this.department = department;
		this.channel = channel;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Revenue [amount=" + amount + ", channel=" + channel + ", department=" + department + ", fee=" + fee
				+ ", month=" + month + "]";
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}