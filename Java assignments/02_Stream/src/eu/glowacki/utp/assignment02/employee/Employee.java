package eu.glowacki.utp.assignment02.employee;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Employee extends Person {

	private BigDecimal _salary;
	private Manager _manager;
	
	protected Employee(String firstName, String surName, Date birthday, BigDecimal salary, Manager manager) {
		super(firstName, surName, birthday);
		_salary = salary;
		_manager = manager;
		
	}

	public BigDecimal get_salary() {
		return _salary;
	}

	public Manager get_manager() {
		return _manager;
	}

	public void set_salary(BigDecimal salary) {
		_salary = salary;
	}

	public void set_manager(Manager manager) {
		_manager = manager;
	}
	
	/*
	public boolean equals(Employee o) {
		return super.equals(o) && _salary.equals(o.get_salary());
	}
	*/
	
}