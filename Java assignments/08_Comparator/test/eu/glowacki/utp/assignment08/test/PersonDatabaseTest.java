package eu.glowacki.utp.assignment08.test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.glowacki.utp.assignment08.Assignment08Exception;
import eu.glowacki.utp.assignment08.Person;
import eu.glowacki.utp.assignment08.PersonDatabase;
import org.junit.Assert;

public class PersonDatabaseTest {

	@Test
	public void serializeAndDeserialize() {	
		List<Person> list = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		PersonDatabase database;
		
		try {
			list.add(new Person("Anna", "Chop", format.parse("1977-01-27")));
			list.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			list.add(new Person("John", "Matthew", format.parse("1998-12-25")));
			list.add(new Person("Pew", "Pork", format.parse("1982-09-02")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		database = new PersonDatabase(list);
		DataOutputStream out;
		try {
			out = new DataOutputStream(new FileOutputStream("input\\database.txt"));
			database.serialize(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Assignment08Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataInputStream in;
		PersonDatabase database2 = null;
		
		try {
			in = new DataInputStream(new FileInputStream("input\\database.txt"));
			database2 = PersonDatabase.deserialize(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Assignment08Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(database, database2);
	}
}