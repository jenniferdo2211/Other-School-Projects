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
	
	// (assignment 03)
		// methods:
		// * is older than other person
		// * is younger than other person
		// * compare age with other person's age

	public boolean olderThan(Person other) {
		return getBirthDay().before(other.getBirthDay());
	}
	
	public boolean youngerThan(Person other) {
		return getBirthDay().after(other.getBirthDay());
	}
	
	public boolean equals(Object other) {
		if (other instanceof Person) {
			Person o = (Person) other;
			return o.getSurName().equals(_surName) && _firstName.equals(o.getFirstName()) 
					&& _birthday.equals(o.getBirthDay());
		}
		return false;
	}
	
}