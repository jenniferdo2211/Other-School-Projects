package eu.glowacki.utp.assignment02.payroll;

import java.math.BigDecimal;

import eu.glowacki.utp.assignment02.employee.Employee;

public final class PayrollEntry{

	private final Employee _employee;
	private final BigDecimal _salaryPlusBonus;
	
	public PayrollEntry(Employee employee, BigDecimal salary, BigDecimal bonus) {
		_employee = employee;
		_salaryPlusBonus = salary.add(bonus); // validate whether salary and bonus are not null
	}

	public boolean equals(PayrollEntry o) {
		// TODO Auto-generated method stub
		return _employee.equals(o.get_employee()) && _salaryPlusBonus.equals(o.get_salaryPlusBonus());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PayrollEntry) {
			return equals((PayrollEntry) obj);
		}
		return false;
	}

	public Employee get_employee() {
		return _employee;
	}

	public BigDecimal get_salaryPlusBonus() {
		return _salaryPlusBonus;
	}
	
	
}