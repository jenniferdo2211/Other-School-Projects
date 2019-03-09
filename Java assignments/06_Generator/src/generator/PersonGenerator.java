package generator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import person.Sex;

public class PersonGenerator {
	
	private final static Random rand = new Random();
	
	private final static DateFormat yy = new SimpleDateFormat("yy");
	private final static DateFormat dd = new SimpleDateFormat("dd");
	private final static NumberFormat MM = new DecimalFormat("00");
	private final static NumberFormat random = new DecimalFormat("000");
	
	final static Set<String> pesels;
	final static List<Locale> nationalities;
	final static Set<String> identifiers;
	final static Map<Locale, String> alphabets;

	static {
		nationalities = Arrays.asList(new Locale("pl", "PL"), new Locale("uk", "UA"), new Locale("be", "BY"),
				new Locale("sk", "SK"), new Locale("lt", "LT"), new Locale("lv", "LV"), new Locale("en", "GB"),
				new Locale("en", "IN"), new Locale("zh", "CN"), new Locale("vi", "VN"));
		identifiers = new HashSet<String>();
		alphabets = new HashMap<Locale, String>();
		pesels = new HashSet<String>();

		int i = 0;
		alphabets.put(nationalities.get(i++), "AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż");
		alphabets.put(nationalities.get(i++), "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя");
		alphabets.put(nationalities.get(i++), "АаБбВвГгДдЕеЁёЖжЗзІіЙйКкЛлМмНнОоПпРрСсТтУуЎўФфХхЦцЧчШшЫыЬьЭэЮюЯя");
		alphabets.put(nationalities.get(i++),
				"AaÁáÄäBbCcČčDdĎďDzdzDždžEeÉéFfGgHhChchIiÍíJjKkLlĹĺĽľMmNnŇňOoÓóÔôPpQqRrŔŕSsŠšTtŤťUuÚúVvWwXxYyÝýZzŽž");
		alphabets.put(nationalities.get(i++), "AaĀāBbCcČčDdEeĒēFfGgĢģHhIiĪīJjKkĶķLlĻļMmNnŅņOoPpRrSsŠšTtUuŪūVvZzŽž");
		alphabets.put(nationalities.get(i++), "ABCDEFGHIJKLMNOPQRSTUVWXYZČŠŽĀĒĢĪĶĻŅŌŖŪāēģīķļņōŗū");
		alphabets.put(nationalities.get(i++), "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz");
		alphabets.put(nationalities.get(i++), "अआइईउऊएऐओऔकखगघङचछजझञटठडढणतथदधनपफबभमयरलवशषसह");
		alphabets.put(nationalities.get(i++), "诶比西迪伊艾弗吉艾尺艾杰开艾勒艾马艾娜哦屁ì吉吾艾儿艾丝提伊吾维豆贝尔维艾克斯吾艾贼德");
		alphabets.put(nationalities.get(i++), "AaĂăÂâBbCcDdĐđEeÊêGgHhIiKkLlMmNnOoÔôƠơPpQqRrSsTtUuƯưVvXxYy");

	}

	public static String generatePESEL(Date birthDate, Sex gender) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		// YYMMDD
		if (year >= 1800 && year <= 1899)
			month += 80;
		else if (year >= 2000 && year <= 2099)
			month += 20;

		StringBuilder sb;
		// ZZZ
		do {

			sb = new StringBuilder();
			sb.append(yy.format(birthDate));
			sb.append(MM.format(month));
			sb.append(dd.format(birthDate));
			
			int ID = rand.nextInt(999 - 100 + 1) + 100;
			sb.append(random.format(ID));

			// X
			sb.append(gender.randomPESELDigit());

			sb.append(controlDigit(sb));
		} while (pesels.contains(sb.toString()) && sb.toString() != null);

		pesels.add(sb.toString());

		return sb.toString();
	}

	private static String controlDigit(StringBuilder builder) {
		final int[] weights = new int[] { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3 };
		final NumberFormat format = new DecimalFormat("0");
		int sum = 0;
		for (int i = 0; i < weights.length; i++) {
			int digit = builder.charAt(i) - '0';
			sum += weights[i] * digit;
		}
		return format.format((10 - sum % 10) % 10);
	}

	public static String generateName(Locale nationality) {
		final String alfabet = alphabets.get(nationality);

		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				char c = alfabet.charAt(rand.nextInt(alfabet.length()));
				if (i == 0)
					c = Character.toUpperCase(c);
				else
					c = Character.toLowerCase(c);
				builder.append(c);
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}

		return builder.toString();
	}

	public static String generateGender() {
		int x = rand.nextInt(2);
		if (x == 0)
			return "male";
		else
			return "female";
	}

	public static Date generateBirthDate() {
		int year = rand.nextInt(2018 - 1918 + 1) + 1918;
		int month = rand.nextInt(12) + 1;
		int day = rand.nextInt(30) + 1;

		Calendar cal = new GregorianCalendar(year, month, day);
		return cal.getTime();
	}

	public static Locale generateNationality() {
		final Random rand = new Random();
		return nationalities.get(rand.nextInt(10));
	}
}