package group;

import java.text.Collator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import person.Teacher;

public class Subject {
	String _subName;
	Department _dept;
	Teacher _lecturer;
	Set<StudentGroup> _stGroups;
	
	public Subject(String subName, Department dept, Teacher l, Set<StudentGroup> sts) {
		_subName = subName;
		_dept = dept;
		_lecturer = l;
		_stGroups = sts;
	}

	public int CompareTo(Subject s) {
		Collator collator = Collator.getInstance(new Locale("pl", "PL"));
		int diff = collator.compare(_subName, s._subName);
		if (diff == 0) {
			diff = collator.compare(_dept, s._dept);
			if (diff == 0) {
				diff = collator.compare(_lecturer, s._lecturer);
				if (diff == 0) {
					diff = _stGroups.size() - s._stGroups.size();
					if (diff == 0) {
						Iterator<StudentGroup> iter1 = _stGroups.iterator();
						Iterator<StudentGroup> iter2 = s._stGroups.iterator();
						for (int i = 0; i < _stGroups.size(); i++) {
							if (diff != 0) {
								break;
							} else {
								diff = collator.compare(iter1.next(), iter2.next());
							}
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
		result = prime * result + ((_dept == null) ? 0 : _dept.hashCode());
		result = prime * result + ((_lecturer == null) ? 0 : _lecturer.hashCode());
		result = prime * result + ((_stGroups == null) ? 0 : _stGroups.hashCode());
		result = prime * result + ((_subName == null) ? 0 : _subName.hashCode());
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
		Subject other = (Subject) obj;
		if (_dept == null) {
			if (other._dept != null)
				return false;
		} else if (!_dept.equals(other._dept))
			return false;
		if (_lecturer == null) {
			if (other._lecturer != null)
				return false;
		} else if (!_lecturer.equals(other._lecturer))
			return false;
		if (_stGroups == null) {
			if (other._stGroups != null)
				return false;
		} else if (!_stGroups.equals(other._stGroups))
			return false;
		if (_subName == null) {
			if (other._subName != null)
				return false;
		} else if (!_subName.equals(other._subName))
			return false;
		return true;
	}

	public boolean isEmpty() {
		if (_dept.isEmpty())
			return true;
		else if (_stGroups.isEmpty())
			return true;
		else if (_lecturer == null)
			return true;
		return false;
	}
	
	
}
