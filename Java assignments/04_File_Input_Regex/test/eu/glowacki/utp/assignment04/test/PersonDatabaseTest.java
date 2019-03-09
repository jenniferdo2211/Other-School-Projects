package eu.glowacki.utp.assignment04.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.glowacki.utp.assignment04.InputParser;
import eu.glowacki.utp.assignment04.Person;
import eu.glowacki.utp.assignment04.PersonDatabase;

public class PersonDatabaseTest {

	List<Person> expected;
	List<Person> actual;
	PersonDatabase database;
	SimpleDateFormat format;
	
	@Before
	public void before() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		List<Person> listAllPersons = InputParser.parse(new File("data/data.txt"));
		database = new PersonDatabase(listAllPersons);
	}

	@Test
	public void sortedByFirstName() {
		expected = new ArrayList<>();
		try {
			expected.add(new Person("Anna", "Chop", format.parse("1977-01-27"))); 
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("Pew", "Pork", format.parse("1982-09-02")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		actual = database.sortedByFirstName(); 
		for (int i = 0; i < expected.size(); i++) {
			Person p1 = expected.get(i);
			Person p2 = actual.get(i);
			Assert.assertEquals(p1.get_firstName(), p2.get_firstName());
			Assert.assertEquals(p1.get_surname(), p2.get_surname());
			Assert.assertEquals(p1.get_birthdate(), p2.get_birthdate());
		}
		
	}
	
	@Test
	public void sortedBySurnameFirstNameAndBirthdate() {
		expected = new ArrayList<>();
		try {
			expected.add(new Person("Anna", "Chop", format.parse("1977-01-27"))); 
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("Pew", "Pork", format.parse("1982-09-02")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actual = database.sortedBySurnameFirstNameAndBirthdate(); 

		for (int i = 0; i < expected.size(); i++) {
			Person p1 = expected.get(i);
			Person p2 = actual.get(i);
			Assert.assertEquals(p1.get_firstName(), p2.get_firstName());
			Assert.assertEquals(p1.get_surname(), p2.get_surname());
			Assert.assertEquals(p1.get_birthdate(), p2.get_birthdate());
		}
	}
	
	@Test
	public void sortedByBirthdate() {
		expected = new ArrayList<>();
		try {
			expected.add(new Person("Anna", "Chop", format.parse("1977-01-27"))); 
			expected.add(new Person("Pew", "Pork", format.parse("1982-09-02")));
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actual = database.sortedByBirthdate(); 
		for (int i = 0; i < expected.size(); i++) {
			Person p1 = expected.get(i);
			Person p2 = actual.get(i);
			Assert.assertEquals(p1.get_firstName(), p2.get_firstName());
			Assert.assertEquals(p1.get_surname(), p2.get_surname());
			Assert.assertEquals(p1.get_birthdate(), p2.get_birthdate());
		}
	}
	
	@Test
	public void bornOnDay() {
		
		try {
			Date theDay = format.parse("1998-12-25");
			expected = new ArrayList<>();
			expected.add(new Person("John", "Matthew", theDay));
			expected.add(new Person("John", "Matthew", theDay));
		
			actual = database.bornOnDay(theDay); 
			
			for (int i = 0; i < actual.size(); i++) {
				Person p1 = expected.get(i);
				Person p2 = actual.get(i);
				Assert.assertEquals(p1.get_firstName(), p2.get_firstName());
				Assert.assertEquals(p1.get_surname(), p2.get_surname());
				Assert.assertEquals(p1.get_birthdate(), p2.get_birthdate());
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
