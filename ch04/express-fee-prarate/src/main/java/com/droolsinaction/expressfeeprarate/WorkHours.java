package com.droolsinaction.expressfeeprarate;

import java.io.Serializable;

public class WorkHours implements Serializable {

	static final long serialVersionUID = 1L;

	private String month;
	private String department;
	private double hours;

	public WorkHours() {
	}

	public WorkHours(String month, String department, double hours) {
		this.month = month;
		this.department = department;
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "WorkHours [department=" + department + ", hours=" + hours + ", month=" + month + "]";
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

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

}