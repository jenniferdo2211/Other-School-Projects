package eu.glowacki.utp.assignment04.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.glowacki.utp.assignment04.InputParser;
import eu.glowacki.utp.assignment04.Person;

public class InputParserTest {
	
	List<Person> expected;
	
	@Before
	public void before() {
		expected = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			expected.add(new Person("Anna", "Chop", format.parse("1977-01-27")));  
			expected.add(new Person("Pew", "Pork", format.parse("1982-09-02")));
			expected.add(new Person("John", "Matthew", format.parse("1998-12-25")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void parse() {
		final File file = new File("data/data.txt");
		List<Person> actual = InputParser.parse(file);
		
		for (int i = 0; i < expected.size(); i++) {
			Person p1 = expected.get(i);
			Person p2 = actual.get(i);
			Assert.assertEquals(p1.get_firstName(), p2.get_firstName());
			Assert.assertEquals(p1.get_surname(), p2.get_surname());
			Assert.assertEquals(p1.get_birthdate(), p2.get_birthdate());
		}
			
	}

}
