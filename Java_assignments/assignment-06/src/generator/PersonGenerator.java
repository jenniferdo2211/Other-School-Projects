package generator;

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

public class PersonGenerator {

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

	public static String generatePESEL(Date birthDate, String gender) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		// YYMMDD
		if (year >= 1800 && year <= 1899)
			month += 80;
		else if (year >= 2000 && year <= 2099)
			month += 20;

		int a = (year % 100) / 10;
		int b = year % 10;

		int c = month / 10;
		int d = month % 10;

		int e = day / 10;
		int f = day % 10;

		StringBuilder sb;
		// ZZZ
		do {

			sb = new StringBuilder();
			sb.append(a);
			sb.append(b);
			sb.append(c);
			sb.append(d);
			sb.append(e);
			sb.append(f);

			Random rand = new Random();
			int ID = rand.nextInt(999 - 100 + 1) + 100;

			int g = ID / 100;
			int h = (ID % 100) / 10;
			int i = ID % 10;
			sb.append(ID++);

			// X
			int j = oddNumber();
			if (gender.equals("male")) {
				sb.append(j);
			} else if (gender.equals("female")) {
				++j;
				sb.append(j);
			} else {
				System.out.println("Gender is invalid");
				System.exit(1);
			}

			// Q
			int checksum = 0;
			checksum = a + b * 3 + c * 7 + d * 9 + e + f * 3 + g * 7 + h * 9 + i + j * 3;

			checksum = checksum % 10;
			checksum = (10 - checksum) % 10;
			sb.append(checksum);
		} while (pesels.contains(sb.toString()) && sb.toString() != null);

		pesels.add(sb.toString());

		return sb.toString();
	}

	private static int oddNumber() {
		// max = 8; min = 0
		int x = (int) (Math.random() * 8);
		if (x % 2 == 1)
			return x;
		else
			return ++x;
	}

	public static String generateName(Locale nationality) {

		final String alfabet = alphabets.get(nationality);

		final Random rand = new Random();

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
		final Random rand = new Random();
		int x = rand.nextInt(2);

		if (x == 0)
			return "male";
		else
			return "female";
	}

	public static Date generateBirthDate() {
		final Random rand = new Random();
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
