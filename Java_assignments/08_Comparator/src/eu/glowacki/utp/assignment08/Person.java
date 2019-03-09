package eu.glowacki.utp.assignment08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

	// assignment 8
	public void serialize(DataOutputStream output) throws Assignment08Exception {
		// serialize birth date with getTime() method
		// encapsulate IOException in Assignment08Exception
		try {
			output.writeUTF(_firstName);
			output.writeUTF(_surname);
			output.writeLong(_birthdate.getTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Person deserialize(DataInputStream input) throws Assignment08Exception {
		try {
			String fname = input.readUTF();
			String sname = input.readUTF();
			Date birth = new Date(input.readLong());
			return new Person(fname, sname, birth);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// natural comparation
	@Override
	public int compareTo(Person other) {
		if (_surname.compareTo(other.get_surname()) != 0) {
			return _surname.compareTo(other.get_surname());
		} else if (_firstName.compareTo(other.get_firstName()) != 0)
			return _firstName.compareTo(other.get_firstName());
		else
			return _birthdate.compareTo(other.get_birthdate());
	}

	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass())
			return false;
		else {
			Person o = (Person)other;
			return this.compareTo(o) == 0;
		}
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