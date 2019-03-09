package jennifer.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pesel {
	private String PESEL;
	
	public Pesel(String p) {
		PESEL = p;
	}
	
	private boolean isPESELValid() {
		Pattern pattern = Pattern.compile("[0-9]{11}");
		Matcher matcher = pattern.matcher(PESEL);
		if (!matcher.matches()) {
			return false;
		}
		int checksum = 0;
		
		for (int i = 0; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(PESEL.substring(i, i+1));
		}
		for (int i = 1; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(PESEL.substring(i, i+1)) * 3;
		}
		for (int i = 2; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(PESEL.substring(i, i+1)) * 7;
		}
		for (int i = 3; i < 10; i += 4) {
			checksum = checksum + Integer.parseInt(PESEL.substring(i, i+1)) * 9;
		}
		checksum = checksum % 10;
		checksum = (10 - checksum) % 10;
		int actualChecksum = Integer.parseInt(PESEL.substring(PESEL.length() -  1, PESEL.length()));
		
		return checksum == actualChecksum;
	}
	
	private Date extractBirthday() {
		String string = PESEL.substring(0,6);
		int month = Integer.valueOf(string.substring(2,4)).intValue();
		if (month > 80) {
			month -= 80;
		} else if (month > 20) {
			month -= 20;
		} else if (month > 40) {
			month -= 40;
		} else if (month > 60) {
			month -= 60;
		}; 
		string.replace(string.substring(2,4), String.valueOf(month));
		
		DateFormat format = new SimpleDateFormat("yyMMdd");
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private Gender extractGender() {
		if (Integer.valueOf(PESEL.substring(10,11)).intValue() % 2 == 0)
			return Gender.FEMALE;
		else
			return Gender.MALE;
	}
	
	public static void main(String[] args) {
		Pesel p = new Pesel("98112207864");
		System.out.println(p.isPESELValid());
		System.out.println(p.extractBirthday());
		System.out.println(p.extractGender());
	}
}
