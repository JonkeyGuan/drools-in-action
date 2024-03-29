package com.droolsinaction.promotion;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class OrderItem implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String orderId;
	private java.lang.String sku;
	private int quant;
	private double amount;
	private double discountAmount;

	public OrderItem() {
	}

	public java.lang.String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getSku() {
		return this.sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public int getQuant() {
		return this.quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
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

	public OrderItem(java.lang.String orderId, java.lang.String sku, int quant,
			double amount, double discountAmount) {
		this.orderId = orderId;
		this.sku = sku;
		this.quant = quant;
		this.amount = amount;
		this.discountAmount = discountAmount;
	}

}