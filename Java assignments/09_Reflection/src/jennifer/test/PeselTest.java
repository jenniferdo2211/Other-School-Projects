package jennifer.test;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jennifer.person.Gender;
import jennifer.person.Pesel;

class PeselTest {
	Pesel pesel;
	
	@BeforeEach
	void setUp() throws Exception {
		pesel = new Pesel("98112207864");
	}

	@Test
	void isPESELValid() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m = c.getDeclaredMethod("isPESELValid");
		boolean check = (boolean) m.invoke(pesel);
		Assert.assertTrue(check);
	}
	
	@Test
	void extractBirthday() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m = c.getDeclaredMethod("extractBirthday");
		Date actual = (Date) m.invoke(pesel);
		
		DateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
		Date expected = format.parse("1998-Nov-22");
		
		Assert.assertEqual(actual, expected);
	}
	
	@Test
	void extractGender() {
		Class<? extends Pesel> c = pesel.getClass();
		Method m = c.getDeclaredMethod("extractGender");
		Gender actual = (Gender) m.invoke(pesel);
		
		Assert.assertEqual(actual, Gender.FEMALE);
	}
}
