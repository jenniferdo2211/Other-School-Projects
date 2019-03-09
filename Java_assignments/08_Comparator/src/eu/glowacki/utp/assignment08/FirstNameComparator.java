package eu.glowacki.utp.assignment08;

import java.util.Comparator;

public class FirstNameComparator implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		// TODO Auto-generated method stub
		return p1.get_firstName().compareTo(p2.get_firstName());
	}
}
