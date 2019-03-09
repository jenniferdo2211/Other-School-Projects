package eu.glowacki.utp.assignment02;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eu.glowacki.utp.assignment02.employee.Employee;
import eu.glowacki.utp.assignment02.employee.Manager;
import eu.glowacki.utp.assignment02.employee.Trainee;
import eu.glowacki.utp.assignment02.employee.Worker;
import eu.glowacki.utp.assignment02.payroll.PayrollEntry;

public final class HumanResourcesStatistics {
	
	//getPayroll
	public static PayrollEntry getPayroll(Employee e) {
		if (e instanceof Worker) {
			Worker worker = (Worker)e;
			return new PayrollEntry(worker, worker.get_salary(), worker.get_bonus());
		}
		else 
			return new PayrollEntry(e, e.get_salary(), BigDecimal.ZERO);
	}
	
	public static List<PayrollEntry> payroll(List<? extends Employee> employees) {
		List<PayrollEntry> payroll = employees
				.stream()
				.map(e -> getPayroll(e))
				.collect(Collectors.toList());
		return payroll;
	}

	public static List<PayrollEntry> subordinatesPayroll(Manager manager) {
		List<PayrollEntry> allSubPayroll = manager.getAllSubordinates()
											.stream()
											.map(e -> getPayroll(e))
											.collect(Collectors.toList());
		return allSubPayroll;
	}
	
	////////////////////////
	public static BigDecimal bonusTotal(List<Employee> employees) {
		BigDecimal total;
		total = employees.stream()
				.map(e -> {
					if (e instanceof Worker)
						return ((Worker) e).get_bonus();
					else 
						return BigDecimal.ZERO;
				})
				.reduce(BigDecimal.ZERO, (part, next) -> part.add(next));
		return total;
	}
	
	/////////////////////////
	private static Date getDate(Employee e) {
		if (e instanceof Trainee) 
			return ((Trainee) e).get_startdate();
		else if (e instanceof Worker)
			return ((Worker) e).get_employmentdate();
		return null;	
	}
	
	public static Employee longestSeniority(List<? extends Employee> emps) {
		Employee longest = emps
				.stream()
				.reduce(null, (part, next) -> {if (part != null && getDate(part).before(getDate(next))) 
														return part;
												else 
														return next;
														});
		return longest;
		
	}
	
	///////////////////////////////
	public static int highestSal(List<Employee> emps) {
		BigDecimal highest = emps
							.stream()
							.map(e -> e.get_salary())
							.reduce(BigDecimal.ZERO, (part, next) -> {
								return (part.compareTo(next) > 0)? part : next;
							});			
		return Integer.valueOf(highest.intValue());
	}
	
	//////////////////////////////
	public static int highestSalWithBonus(List<? extends Employee> emps) {
		BigDecimal highest = emps
				.stream()
				.map(e -> {
					if (e instanceof Worker) 
						return e.get_salary().add( ((Worker) e).get_bonus() );
					else 
						return e.get_salary();
				})
				.reduce(BigDecimal.ZERO, (part, next) -> {
					return (part.compareTo(next) > 0)? part : next;
				});	
		return Integer.valueOf(highest.intValue());
	}
	
	/////////////////////////////
	public static List<Employee> getEmployeeRegex(Manager manager) {
		List<Employee> list = manager.getAllSubordinates()
									.stream()
									.filter(e -> e.getSurName().startsWith("A"))
									.collect(Collectors.toList());
		return list;
	}
	
	////////////////////////////
	public static List<Employee> getMoreThanThousand(List<Employee> emps) {
		List<Employee> list = emps.stream()
							.map(e -> {
								if (e instanceof Worker) {
									BigDecimal earning = e.get_salary().add( ((Worker) e).get_bonus() );
									if (earning.compareTo(new BigDecimal(1000)) > 0)
										return e;
								}
								else {
									BigDecimal earning = e.get_salary();
									if (earning.compareTo(new BigDecimal(1000)) > 0)
										return e;
								}
									return null;
							})
							.filter(e -> e != null)
							.collect(Collectors.toList());
		return list;
	}

}