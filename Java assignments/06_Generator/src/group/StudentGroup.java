package group;

import java.text.Collator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import person.Student;

public class StudentGroup {

	String _stGroupName;
	Set<Student> _students;

	public StudentGroup(String name, Set<Student> list) {
		_stGroupName = name;
		_students = list;
	}

	public StudentGroup(String name) {
		_stGroupName = name;
		_students = new HashSet<>();
	}

	public boolean isFull() {
		return _students.size() == 10;
	}

	public void addStudent(Student st) {
		_students.add(st);
	}

	public void removeStudent(Student st) {
		_students.remove(st);
	}
	
	public Set<Student> getStudentGroup() {
		return _students;
	}
	
	public boolean isEmpty() {
		return _students.isEmpty();
	}

	public int CompareTo(StudentGroup s) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		
		int diff = collator.compare(_stGroupName, s._stGroupName);
		if (diff == 0) {
			diff = _students.size() - s._students.size();
			if (diff == 0) {
				Iterator<Student> iter1 = _students.iterator();
				Iterator<Student> iter2 = s._students.iterator();
				for (int i = 0; i < _students.size(); i++) {
					if (diff != 0) {
						break;
					} else {
						diff = collator.compare(iter1.next(), iter2.next());
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
		result = prime * result + ((_stGroupName == null) ? 0 : _stGroupName.hashCode());
		result = prime * result + ((_students == null) ? 0 : _students.hashCode());
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
		StudentGroup other = (StudentGroup) obj;
		if (_stGroupName == null) {
			if (other._stGroupName != null)
				return false;
		} else if (!_stGroupName.equals(other._stGroupName))
			return false;
		if (_students == null) {
			if (other._students != null)
				return false;
		} else if (!_students.equals(other._students))
			return false;
		return true;
	}

}
