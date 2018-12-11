package person;

import java.text.Collator;
import java.util.Date;
import java.util.Locale;

public abstract class Person implements Comparable<Person>{
	
	private final String PESEL;
	private String _surname;
	private String _firstName;
	private String _gender;
	private Date _birthDate;
	private Locale _nationality;
	
	public Person(String pesel, String sname, String fname, String gender, Date birthDate, Locale nation) {
		PESEL = pesel;
		_surname = sname;
		_firstName = fname;
		_gender = gender;
		_birthDate = birthDate;
		_nationality = nation;		
	}
	
	public Person(Person p) {
		PESEL = p.getPESEL();
		_surname = p.get_surname();
		_firstName = p.get_firstName();
		_gender = p.get_gender();
		_birthDate = p.get_birthDate();
		_nationality = p.get_nationality();
	}
	
	public int compareTo(Person p) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		int diff = PESEL.compareTo(p.getPESEL());
		if (diff == 0) {
			diff = collator.compare(_surname, p._surname);
			if (diff == 0) {
				diff = collator.compare(_firstName, p._firstName);
				if (diff == 0) {
					diff = collator.compare(_gender, p._gender);
					if (diff == 0) {
						diff = collator.compare(_birthDate, p._birthDate);
						if (diff == 0) {
							diff = collator.compare(_nationality, p._nationality);
						}
					}
				}
			}
		}
		
		return diff;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PESEL == null) ? 0 : PESEL.hashCode());
		result = prime * result + ((_birthDate == null) ? 0 : _birthDate.hashCode());
		result = prime * result + ((_firstName == null) ? 0 : _firstName.hashCode());
		result = prime * result + ((_gender == null) ? 0 : _gender.hashCode());
		result = prime * result + ((_surname == null) ? 0 : _surname.hashCode());
		result = prime * result + ((_nationality == null) ? 0 : _nationality.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		
		if (PESEL == null) {
			if (other.PESEL != null)
				return false;
		} else if (!PESEL.equals(other.PESEL))
			return false;
		
		if (_birthDate == null) {
			if (other._birthDate != null)
				return false;
		} else if (!_birthDate.equals(other._birthDate))
			return false;
		if (_firstName == null) {
			if (other._firstName != null)
				return false;
		} else if (!_firstName.equals(other._firstName))
			return false;
		if (_gender == null) {
			if (other._gender != null)
				return false;
		} else if (!_gender.equals(other._gender))
			return false;
		if (_surname == null) {
			if (other._surname != null)
				return false;
		} else if (!_surname.equals(other._surname))
			return false;
		if (_nationality == null) {
			if (other._nationality != null)
				return false;
		} else if (!_nationality.equals(other._nationality))
			return false;
		return true;
	}

	public String getPESEL() {
		return PESEL;
	}

	public String get_surname() {
		return _surname;
	}

	public String get_firstName() {
		return _firstName;
	}

	public String get_gender() {
		return _gender;
	}

	public Date get_birthDate() {
		return _birthDate;
	}

	public Locale get_nationality() {
		return _nationality;
	}

}







