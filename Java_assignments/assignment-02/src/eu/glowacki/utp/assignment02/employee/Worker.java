package eu.glowacki.utp.assignment02.employee;

import java.math.BigDecimal;
import java.util.Calendar;
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
	
	// (assignment 03)
		// attributes:
		// * has bonus
		
		// methods:
		// * seniority is longer than given number of years (seniority - sta¿)
		// * seniority is longer than given number of months
		// * has bonus greater than given amount of money
	
	public int getYear() {
		Calendar calendar = Calendar.getInstance();
		
		Date today = new Date();
		calendar.setTime(today);
		int end = calendar.get(Calendar.YEAR);
		
		calendar.setTime(_employmentdate);
		int start = calendar.get(Calendar.YEAR);
		
		return end - start;
	}
	
	public int getMonth() {
		Calendar calendar = Calendar.getInstance();
		
		Date today = new Date();
		calendar.setTime(today);
		int end = calendar.get(Calendar.MONTH);
		
		calendar.setTime(_employmentdate);
		int start = calendar.get(Calendar.MONTH);
		
		return end - start + getYear();
	}
	
	public boolean seniorityLongerThanYears(int num) {
		return getYear() > num;
	}
	
	public boolean seniorityLongerThanMonths(int num) {
		return getMonth() > num;
	}
	
	public boolean bonusGreaterThan(BigDecimal amount) {
		return get_bonus().compareTo(amount) > 0;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Worker && other != null) {
			Worker o = (Worker) other;
			return super.equals(o) && _employmentdate.equals(o.get_employmentdate()) && _bonus.equals(o.get_bonus());		
		}
		return false;
	}
	
}