package person;

import java.util.Random;

public enum ACADEMIC_DEGREE {
	MA, BA, PHD, PROFESSOR;
	
	private static final Random RANDOM = new Random();
	
	
	public static ACADEMIC_DEGREE random() {
		int random = RANDOM.nextInt(values().length);
		return values()[random];
	}
	
}