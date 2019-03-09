package group;

import java.text.Collator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import person.Teacher;

public class Department {
	String _deptName;
	Set<Teacher> _teachers;
	
	public Department(String deptName, Set<Teacher> teachers) {
		_deptName = deptName;
		_teachers = teachers;
	}
	
	public Department(String name) {
		_deptName = name;
		_teachers = new HashSet<>();
	}
	
	public void addTeacher(Teacher t) {
		_teachers.add(t);
	}
	
	public void removeTeacher(Teacher t) {
		_teachers.remove(t);
	}

	public Teacher getLecturer() {
		Iterator<Teacher> iter = _teachers.iterator();
		if (iter.hasNext())
			return iter.next();
		else 
			return null;
	}
	
	public Set<Teacher> getTeacherList() {
		return _teachers;
	}
	
	public int compareTo(Department d) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		int diff = collator.compare(_deptName, d._deptName);
		
		if (diff == 0) {
			diff = _teachers.size() - d._teachers.size();
			if (diff == 0) {
				Iterator<Teacher> iter1 = _teachers.iterator();
				Iterator<Teacher> iter2 = d._teachers.iterator();
				for (int i = 0; i < _teachers.size(); i++) {
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
		result = prime * result + ((_deptName == null) ? 0 : _deptName.hashCode());
		result = prime * result + ((_teachers == null) ? 0 : _teachers.hashCode());
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
		Department other = (Department) obj;
		if (_deptName == null) {
			if (other._deptName != null)
				return false;
		} else if (!_deptName.equals(other._deptName))
			return false;
		if (_teachers == null) {
			if (other._teachers != null)
				return false;
		} else if (!_teachers.equals(other._teachers))
			return false;
		return true;
	}

	public boolean isEmpty() {
		return _teachers.isEmpty();
	}
	
	
}
