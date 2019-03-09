package test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import generator.PersonGenerator;
import person.Sex;

import org.junit.Assert;



class PersonGeneratorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void generatePESEL() {
		Calendar cal = new GregorianCalendar(2009, 10, 22);	
		String actual = PersonGenerator.generatePESEL(cal.getTime(), Sex.Female);
		Pattern pattern = Pattern.compile("093022[0-9]{3}[02468]");
		Matcher matcher = pattern.matcher(actual);
		
		int checksum = 0;
		
		for (int i = 0; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(actual.substring(i, i+1));
		}
		
		for (int i = 1; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(actual.substring(i, i+1)) * 3;
		}
		
		for (int i = 2; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(actual.substring(i, i+1)) * 7;
		}
		
		for (int i = 3; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(actual.substring(i, i+1)) * 9;
		}
		
		checksum = checksum % 10;
		checksum = (10 - checksum) % 10;
		
		boolean x = matcher.find();
		int actualChecksum = Integer.parseInt(actual.substring(actual.length() -  1, actual.length()));
		
		Assert.assertTrue(x);
		Assert.assertEquals(checksum, actualChecksum);
		
		//a + b * 3 + c * 7 + d * 9 + e + f * 3 + g * 7 + h * 9 + i + j * 3;
		//0	  1		  2		  3		  4   5		  6		  7	      8	  9
	}

}
