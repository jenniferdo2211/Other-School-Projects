package person;

import java.text.Collator;
import java.util.Date;
import java.util.Locale;

public class Student extends Person {

	private String _studentNo;
	
	public Student(String studentNo, String pesel, String sname, String fname, String gender, Date birthDate, Locale nation) {
		super(pesel, sname, fname, gender, birthDate, nation);
		_studentNo = studentNo;
	}

	public String getStudentNumber() {
		return _studentNo;
	}

	public int compareTo(Student other) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		
		int diff = collator.compare(_studentNo, other._studentNo);
		if (diff == 0) {
			diff = super.compareTo(other);
		}
		return diff;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_studentNo == null) ? 0 : _studentNo.hashCode());
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
		Student other = (Student) obj;
		if (_studentNo == null) {
			if (other._studentNo != null)
				return false;
		} else if (!_studentNo.equals(other._studentNo))
			return false;
		return true;
	}
	
}
