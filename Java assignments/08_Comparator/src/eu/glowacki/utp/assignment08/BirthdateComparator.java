package eu.glowacki.utp.assignment08;

import java.util.Comparator;

public final class BirthdateComparator implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		return p1.get_birthdate().compareTo(p2.get_birthdate());
	}
}