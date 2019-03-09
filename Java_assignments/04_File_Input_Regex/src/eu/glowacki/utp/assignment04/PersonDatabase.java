package eu.glowacki.utp.assignment04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import eu.glowacki.utp.assignment04.comparators.BirthdateComparator;
import eu.glowacki.utp.assignment04.comparators.FirstNameComparator;

public final class PersonDatabase {
	
	List<Person> list;
	private final Map<Date, List<Person>> _map;
	
	public PersonDatabase(List<Person> list) {
		this.list = list;
		_map = this.list
				.stream()
				.collect(Collectors.groupingBy(Person::get_birthdate, TreeMap::new, Collectors.mapping(p -> p, Collectors.toList())));
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
}