package com.droolsinaction.gift;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Customer implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String name;
	private int points;

	public Customer() {
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Customer(java.lang.String name, int points) {
		this.name = name;
		this.points = points;
	}

}