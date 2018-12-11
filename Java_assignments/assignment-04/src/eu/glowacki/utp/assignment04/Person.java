package eu.glowacki.utp.assignment04;

import java.util.Date;

public class Person implements Comparable<Person> {
	
	private final String _firstName;
	private final String _surname;
	private final Date _birthdate;
	
	public Person(String firstName, String surname, Date birthdate) {
		_firstName = firstName;
		_surname = surname;
		_birthdate = birthdate;
	}

	//natural comparation
	@Override
	public int compareTo(Person other) {
		if (_surname.compareTo(other.get_surname()) != 0) {
			return _surname.compareTo(other.get_surname());
		}
		else if (_firstName.compareTo(other.get_firstName()) != 0)
			return _firstName.compareTo(other.get_firstName());
		else
			return _birthdate.compareTo(other.get_birthdate());
	}
	
	public String toString() {
		return _firstName + " " + _surname + " " + _birthdate + "\n";
	}

	public String get_firstName() {
		return _firstName;
	}

	public String get_surname() {
		return _surname;
	}

	public Date get_birthdate() {
		return _birthdate;
	}
}