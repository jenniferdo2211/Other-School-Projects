package eu.glowacki.utp.assignment03.employee;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Employee extends Person {

		private BigDecimal _salary;
		private Manager _manager;
		
		protected Employee(String firstName, String surName, Date birthday, BigDecimal salary, Manager manager) {
			super(firstName, surName, birthday);
			_salary = salary;
			_manager = manager;
			
		}

		public BigDecimal get_salary() {
			return _salary;
		}

		public Manager get_manager() {
			return _manager;
		}

		public void set_salary(BigDecimal salary) {
			_salary = salary;
		}

		public void set_manager(Manager manager) {
			_manager = manager;
		}
		
		// (assignment 03)
		// methods:
		// * salary is greater than given amount of money
		// * salary is less than given amount of money
		// * compare salary with other employee salary

		public boolean greaterThan(BigDecimal amount) {
			return get_salary().compareTo(amount) > 0;
		}
		
		public boolean lessThan(BigDecimal amount) {
			return get_salary().compareTo(amount) < 0;
		}
		
		public boolean greaterThanOther(Employee e) {
			return get_salary().compareTo(e.get_salary()) > 0;
		}
		
		public boolean equals(Object other) {
			if (other instanceof Employee) {
				Employee o = (Employee) other;
				return super.equals(o) && _salary.equals(o.get_salary()) && _manager.equals(o.get_manager());
			}
			return false;
		}
	}

	