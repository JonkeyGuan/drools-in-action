package com.droolsinaction.promotion;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Product implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String sku;
	private java.lang.String name;
	private double price;

	public Product() {
	}

	public java.lang.String getSku() {
		return this.sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product(java.lang.String sku, java.lang.String name, double price) {
		this.sku = sku;
		this.name = name;
		this.price = price;
	}

}