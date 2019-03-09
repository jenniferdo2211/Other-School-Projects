package eu.glowacki.utp.assignment08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PersonDatabase {

	List<Person> list;
	private final Map<Date, List<Person>> _map;

	public PersonDatabase(List<Person> list) {
		this.list = list;
		_map = this.list.stream().collect(Collectors.groupingBy(Person::get_birthdate)); // TreeMap::new,
																							// Collectors.mapping(p ->
																							// p,
																							// Collectors.toList())));
	} // Function for group by (key), Supplier (for storing), List of persons (from
		// person to person - not change)

	// assignment 8 - factory method based on deserialization
	public static PersonDatabase deserialize(DataInputStream input) throws Assignment08Exception {
		List<Person> list = new ArrayList<>();

		try {
			while (input.available() != 0) {
				Person p = Person.deserialize(input);
				list.add(p);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PersonDatabase(list);

	}

	// assignment 8
	public void serialize(DataOutputStream output) throws Assignment08Exception {
		for (Person p : list) {
			p.serialize(output);
		}
	}

	public boolean equals(Object other) {
		if ((other == null && this != null) || (other.getClass() != getClass())) {
			return false;
		} else {
			PersonDatabase base = (PersonDatabase) other;
			if (list.equals(base.list))
				return true;
		}
		return false;
	}

	public List<Person> sortedByFirstName() {
		Collections.sort(list, new FirstNameComparator());
		return list;
	}

	public List<Person> sortedBySurnameFirstNameAndBirthdate() {
		Collections.sort(list);
		return list;
	}

	public List<Person> sortedByBirthdate() {
		Collections.sort(list, new BirthdateComparator());
		return list;
	}

	public List<Person> bornOnDay(Date date) {
		return _map.get(date);
	}

	public String toString() {
		return list.toString();
	}
}