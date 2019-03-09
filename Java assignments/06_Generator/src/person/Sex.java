package person;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public enum Sex {

	Male,
	Female;
	
	private static final Random RANDOM = new Random();
	private static final NumberFormat FORMAT = new DecimalFormat("0");
	
	public static Sex random() {
		int random = RANDOM.nextInt(values().length);
		return values()[random];
	}
	
	public String randomPESELDigit() {
		return FORMAT.format(random(this));
	}
	
    private static int random(Sex sex) {
    	switch (sex) {
    	case Male:
    		final int maleDigit = randomPESELDigitImpl();
    		return maleDigit % 2 == 1 ? maleDigit : maleDigit + 1;
    	
    	case Female:
    	default:
    		final int femaleDigit = randomPESELDigitImpl();
    		return femaleDigit % 2 == 0 ? femaleDigit : femaleDigit - 1;
    	}
    }
    
    private static int randomPESELDigitImpl() {
    	return RANDOM.nextInt(10);
    }
}