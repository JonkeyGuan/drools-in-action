package com.droolsinaction.promotion;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Order implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String id;
	private double amount;
	private double discountAmount;

	public Order() {
	}

	public java.lang.String getId() {
		return this.id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Order(java.lang.String id, double amount, double discountAmount) {
		this.id = id;
		this.amount = amount;
		this.discountAmount = discountAmount;
	}

}