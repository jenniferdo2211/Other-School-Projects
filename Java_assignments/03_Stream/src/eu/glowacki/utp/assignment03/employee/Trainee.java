package eu.glowacki.utp.assignment03.employee;

import java.math.BigDecimal;
import java.util.Date;


public class Trainee extends Employee {

	// attributes:
	// * apprenticeship start date
	// * apprenticeship length (in days)
	
	Date _startdate;
	int _length;
	
	public Trainee(String firstName, String surName, Date birthday, BigDecimal salary, Manager manager, Date startdate, int length) {
		super(firstName, surName, birthday, salary, manager);
		_startdate = startdate;
		 _length =  length;
	}

	public Date get_startdate() {
		return _startdate;
	}

	public int get_length() {
		return _length;
	}

	public void set_startdate(Date startdate) {
		this._startdate = startdate;
	}

	public void set_length(int length) {
		this._length = length;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Trainee) {
			Trainee o = (Trainee) other;
			return super.equals(o) && _startdate.equals(o.get_startdate()) && _length == o.get_length();
		}
		return false;
	}
	
	// (assignment 03)
		// * practice length is shorter than given number of days
		// * practice length is longer than given number of days
	public boolean longerThan(int days) {
		return get_length() > days;
	}
	
	public boolean shorterThan(int days) {
		return get_length() < days;
	}
	
}
