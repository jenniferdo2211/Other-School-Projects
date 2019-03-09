package eu.glowacki.utp.assignment02.employee;

import java.util.Calendar;
import java.util.Date;

public abstract class Person {

	private final String _firstName; // backing field
	private final String _surName;
	private final Date _birthday;
	
	protected Person(String firstName, String surName, Date birthday) {
		_firstName = firstName;
		_surName = surName;
		_birthday = birthday;
	}

	public String getFirstName() { // getter
		return _firstName;
	}
	
	public String getSurName() { // getter
		return _surName;
	}
	
	public Date getBirthDay() { // getter
		return _birthday;
	}
	
	public short getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int end = calendar.get(Calendar.YEAR);
		calendar.setTime(_birthday);
		int start = calendar.get(Calendar.YEAR);
		
		return (short) (end - start);
	}
	
	/*
	public boolean equals(Person o) {
		return o.getSurName().equals(_surName) && _firstName.equals(o.getFirstName()) 
				&& _birthday.equals(o.getBirthDay());
	}
	*/
	
}


// To implement an attribute means that you provide a backing field and
// getter or optionally setter for read-write properties/attributes
// 
// NO BACKING FIELDS SHOULD BE PROVIDED FOR DERIVED ATTRIBUTES
// THOSE SHOULD BE COMPUTED ON-LINE
//
// attributes:
// * first name (read-only)
// * surname (read-only)
// * birth date (read-only) --- date MUST BE represented by an instance of
// type designed for date representation (either Date or LocalDate)
//
// * age (derived --- computed based on birth date) --- implemented as a
// getter calculating the difference between the current date and birth date
