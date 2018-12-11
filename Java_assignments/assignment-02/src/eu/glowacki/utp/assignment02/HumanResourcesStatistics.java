package eu.glowacki.utp.assignment02;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import eu.glowacki.utp.assignment02.employee.Employee;
import eu.glowacki.utp.assignment02.employee.Trainee;
import eu.glowacki.utp.assignment02.employee.Worker;

public final class HumanResourcesStatistics {
	
	private static <T> void process(List<T> list, Consumer<T> c) {
		// TODO Auto-generated method stub
		for (T t : list)
			c.accept(t);
	}
	
	private final static int getPeriod(Employee e) {
		if (e instanceof Worker) {
			Worker worker = (Worker) e;
			return worker.getMonth();
		}
		
		if (e instanceof Trainee) {
			Trainee trainee = (Trainee) e;
			return trainee.get_length() / 30;
		}
		
		return 0;
	}
	
	
	private static BigDecimal getEarning(Employee e) {
		// TODO Auto-generated method stub
		if (e instanceof Worker) {
			Worker worker = (Worker) e;
			return worker.get_salary().add(worker.get_bonus());
		}
		
		if (e instanceof Trainee) {
			Trainee trainee = (Trainee) e;
			return trainee.get_salary();
		}
		
		return null;
	}
	
	private static boolean earnMore(Employee e1, Employee e2) {
		return getEarning(e1).compareTo(getEarning(e2)) > 0;
	}
	
	// * search for Employees older than given employee and earning less than him
	public static List<Employee> olderThanAndEarnMore(List<Employee> allEmployees, Employee employee) {
		Predicate<Employee> p1 = e -> e.olderThan(employee);
		Predicate<Employee> p2 = e -> earnMore(e, employee);
		Predicate<Employee> p3 = p1.and(p2);
		List<Employee> list = allEmployees.stream()
							.filter(p3)
							.collect(Collectors.toList());		
		return list;
	}

	////////////////////////////////////////////////////////
	// * search for Trainees whose practice length is longer than given number of days and raise their salary by 5%
	public static List<Trainee> practiceLengthLongerThan(List<Employee> allEmployees, int daysCount) {
		List<Trainee> list = allEmployees.stream()
							.filter(e -> e instanceof Trainee)
							.map(e -> (Trainee) e)
							.filter(e -> e.longerThan(daysCount))
							.collect(Collectors.toList());
		
		process(list, t -> t.set_salary(new BigDecimal(
												Integer.valueOf(t.get_salary().intValue()) * 1.05)
										));		
		return list;
	}
	

	// * search for Workers whose seniority is longer than given number of months and give them bonus of 300 if their bonus is smaller
	public static List<Worker> seniorityLongerThan(List<Employee> allEmployees, int monthCount) {
		List<Worker> list = allEmployees.stream()
							.filter(e -> e instanceof Worker)
							.map(e -> (Worker) e)
							.filter(e -> e.seniorityLongerThanMonths(monthCount))
							//.filter(e -> !e.bonusGreaterThan(new BigDecimal(300)))
							.collect(Collectors.toList());
		process(list, w -> { 
							if (!w.bonusGreaterThan(new BigDecimal(300))) 
									w.set_bonus(new BigDecimal(300));
							}
				);
		return list;
	}
	
	//
	// * search for Workers whose seniority is between 1 and 3 years and give them raise of salary by 10%
	public static List<Worker> seniorityBetweenOneAndThreeYears(List<Employee> allEmployees) {
		List<Worker> list = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.seniorityLongerThanYears(1))
				.filter(e -> !e.seniorityLongerThanYears(3))
				.collect(Collectors.toList());
		process(list, w -> w.set_salary(new BigDecimal(Integer.valueOf(w.get_salary().intValue()) * 1.10)));		
		return list;
	}
	
	// * search for Workers whose seniority is longer than the seniority of a given employee and earn less than him and align their salary with the given employee
	public static List<Worker> seniorityLongerThanButEarnLess(List<Employee> allEmployees, Employee employee) {
		List<Worker> list = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.seniorityLongerThanMonths(getPeriod(employee)))
				.filter(e -> earnMore(employee, e))
				.collect(Collectors.toList());	
		process(list, w -> w.set_salary(employee.get_salary()));
		return list;
	}
	
	//
	// * search for Workers whose seniority is between 2 and 4 years and whose age is greater than given number of years
	public static List<Worker> seniorityBetweenTwoAndFourYearsAndAgeGreaterThan(List<Employee> allEmployees, int age) {
		List<Worker> list = allEmployees.stream()
				.filter(e -> e instanceof Worker)
				.map(e -> (Worker) e)
				.filter(e -> e.seniorityLongerThanYears(2))
				.filter(e -> !e.seniorityLongerThanYears(4))
				.filter(e -> e.getAge() > age)
				.collect(Collectors.toList());
		return list;
	}

}