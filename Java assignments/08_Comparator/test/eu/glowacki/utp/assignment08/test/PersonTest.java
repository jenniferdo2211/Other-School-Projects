package eu.glowacki.utp.assignment08.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import eu.glowacki.utp.assignment08.Assignment08Exception;
import eu.glowacki.utp.assignment08.Person;
import org.junit.Assert;

public class PersonTest {

	@Test
	public void serializeAndDeserialize() {
		try {
			Date birth = new GregorianCalendar(2000, 10, 22).getTime();
			Person p1 = new Person("Mike", "Smith", birth);
			DataOutputStream out = new DataOutputStream(new FileOutputStream("input\\input.txt"));
			p1.serialize(out);
			
			DataInputStream in = new DataInputStream(new FileInputStream("input\\input.txt"));
			Person p2 = Person.deserialize(in);
			
			Assert.assertEquals(p1, p2);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Assignment08Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}