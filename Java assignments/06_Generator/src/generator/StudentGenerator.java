package generator;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import person.Sex;
import person.Student;

public class StudentGenerator {
	private static Set<String> studentNumbers = new HashSet<>();

	public static Student next() {

		final Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			for (int i = 0; i < 6; i++) {
				String c = String.valueOf(rand.nextInt(10));
				builder.append(c);
			}
			if (studentNumbers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}

		Date birthDate = PersonGenerator.generateBirthDate();
		Sex gender = Sex.random();
		Locale nationality = PersonGenerator.generateNationality();

		return new Student(builder.toString(), PersonGenerator.generatePESEL(birthDate, gender),
				PersonGenerator.generateName(nationality), PersonGenerator.generateName(nationality), gender, birthDate, nationality);
		
	}

}
