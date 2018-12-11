package eu.glowacki.utp.assignment02.employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Manager extends Worker {

	// attributes
	// * subordinates (a list of immediate subordinates)
	// * all subordinates (derived --- i.e. calculated on the fly --- a list of subordinates in all hierarchy)
	
	List<Employee> _subordinates;
	
	public Manager(String firstName, String surName, Date birthday, BigDecimal salary, Manager manager, Date employmentdate, BigDecimal bonus) {
		super(firstName, surName, birthday, salary, manager, employmentdate, bonus);
		_subordinates = new ArrayList<>(); 
	}
	
	public List<Employee> get_subordinates() {
		return _subordinates;
	}
	
	public List<Employee> getAllSubordinates() {
		ArrayList<Employee> list = new ArrayList<>();
		for (Employee e : _subordinates) {
			list.add(e);
			if (e instanceof Manager)
				list.addAll(((Manager) e).getAllSubordinates());	
		}
		return list;
	}

	public void set_subordinates(List<Employee> subordinates) {
		this._subordinates = subordinates;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Manager && other!= null) {
			Manager o = (Manager) other;
			return super.equals(o) && _subordinates.equals(o.get_subordinates());
		}
		return false;
	}
	
}