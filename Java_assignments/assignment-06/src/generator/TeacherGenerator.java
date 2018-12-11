package generator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import person.ACADEMIC_DEGREE;
import person.Teacher;

public class TeacherGenerator {
	
	public static Teacher next() {
		Date birthDate = PersonGenerator.generateBirthDate();
		String gender = PersonGenerator.generateGender();
		Locale nationality = PersonGenerator.generateNationality();
		
		final Random rand = new Random();
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);
		int birthYear = cal.get(Calendar.YEAR);
		
		int year = rand.nextInt(2018 - birthYear + 1) + birthYear;
		int month = rand.nextInt(12) + 1;
		int day = rand.nextInt(30) + 1;
		
		Calendar cal2 = new GregorianCalendar(year, month, day);	
		
		Date hiredate = cal2.getTime();
		ACADEMIC_DEGREE degree = ACADEMIC_DEGREE.values()[rand.nextInt(4)];
		
		return new Teacher(degree, hiredate, PersonGenerator.generatePESEL(birthDate, gender),
				PersonGenerator.generateName(nationality), PersonGenerator.generateName(nationality), gender, birthDate, nationality);
		
	}
}
