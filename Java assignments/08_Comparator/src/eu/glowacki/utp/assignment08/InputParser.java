package eu.glowacki.utp.assignment08;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputParser {
	
	//Patterns for John Matthew 1998-12-25
	final static String dataRegex = "^(?<firstName>[A-Z][a-z]*)\\s"
			+ "(?<surName>[A-Z][a-z]*)\\s"
			+ "(?<birthday>1[0-9]{3}-(1[0-2]|0[0-9])-[0-3][0-9])$";
	
	
	//"^([A-Z][a-z]*)\\s([A-Z][a-z]*)\\s([0-9]{4}-[0-9]{2}-[0-9]{2})$";
	
	public static List<Person> parse(File file) {
		List<Person> list = new ArrayList<>();
		String line;
		Pattern pattern = Pattern.compile(dataRegex);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		BufferedReader br = null;
		
		if (file.exists() && file.isFile() && file.canRead()) {
			try {
				br = new BufferedReader(new FileReader(file));
				
				while ((line = br.readLine()) != null) {
					Matcher matcher = pattern.matcher(line);
					if (matcher.matches()) {
						Date birthday = format.parse(matcher.group("birthday"));
						list.add(new Person(matcher.group("firstName"), matcher.group("surName"), birthday));
					}
				}
				
			} catch (IOException | ParseException e) {
				System.out.println("File error");
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		return list;
	}
}