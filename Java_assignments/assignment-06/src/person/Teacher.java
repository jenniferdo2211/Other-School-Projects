package person;

import java.text.Collator;
import java.util.Date;
import java.util.Locale;

public class Teacher extends Person {

	ACADEMIC_DEGREE _degree;
	Date _hiredate;
	
	public Teacher(ACADEMIC_DEGREE degree, Date hiredate, String pesel, String sname, String fname, String gender, Date birthDate, Locale nation) {
		super(pesel, sname, fname, gender, birthDate, nation);
		_degree = degree;
		_hiredate = hiredate;
	}

	public int compareTo(Teacher t) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		
		int diff = collator.compare(_degree, t._degree);
		if (diff == 0) {
			diff = collator.compare(_hiredate, t._hiredate);
			if (diff == 0)
				super.compareTo(t);
		}
		
		return diff;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_degree == null) ? 0 : _degree.hashCode());
		result = prime * result + ((_hiredate == null) ? 0 : _hiredate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (_degree != other._degree)
			return false;
		if (_hiredate == null) {
			if (other._hiredate != null)
				return false;
		} else if (!_hiredate.equals(other._hiredate))
			return false;
		return true;
	}

	
}
