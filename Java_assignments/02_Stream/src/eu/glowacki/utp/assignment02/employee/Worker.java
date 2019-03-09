package eu.glowacki.utp.assignment02.employee;

import java.math.BigDecimal;
import java.util.Date;

public class Worker extends Employee {

	// attributes
	// * employment date
	// * bonus
	
	Date _employmentdate;
	BigDecimal _bonus;
	
	public Worker(String firstName, String surName, Date birthday, BigDecimal salary, Manager manager, Date employmentdate, BigDecimal bonus) {
		super(firstName, surName, birthday, salary, manager);
		_employmentdate = employmentdate;
		_bonus = bonus;
	}

	public Date get_employmentdate() {
		return _employmentdate;
	}

	public BigDecimal get_bonus() {
		return _bonus;
	}

	public void set_employmentdate(Date employmentdate) {
		this._employmentdate = employmentdate;
	}

	public void set_bonus(BigDecimal bonus) {
		this._bonus = bonus;
	}
	
	/*
	public boolean equals(Worker o) {
		return super.equals(o) && _employmentdate.equals(o.get_employmentdate()) && _bonus.equals(o.get_bonus());
	}
	*/
	
}