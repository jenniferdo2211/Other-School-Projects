package jennifer.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;

import jennifer.person.Gender;
import jennifer.person.Pesel;
import org.junit.Test;

public class PeselTest {
	Pesel pesel;
	
	@Before
	public void setUp() throws Exception {
		pesel = new Pesel("98112207864");
	}

	@Test
	public void isPESELValid() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m;
		boolean check = false;
		try {
			m = c.getDeclaredMethod("isPESELValid");
			m.setAccessible(true);
			check = (boolean) m.invoke(pesel);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(check);
	}
	
	@Test
	public void extractBirthday() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date actualDate = null;
		try {
			m = c.getDeclaredMethod("extractBirthday");
			m.setAccessible(true);
			actualDate = (Date) m.invoke(pesel);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String actual = format.format(actualDate);
		System.out.println(actual);
		
		Date expected = null;
		
		try {
			expected = format.parse("1998-11-22");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(actual, format.format(expected));
	}
	
	@Test
	public void extractGender() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m;
		Gender actual = null;
		try {
			m = c.getDeclaredMethod("extractGender");
			m.setAccessible(true);
			actual = (Gender) m.invoke(pesel);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals(actual, Gender.FEMALE);
	}
	
}
