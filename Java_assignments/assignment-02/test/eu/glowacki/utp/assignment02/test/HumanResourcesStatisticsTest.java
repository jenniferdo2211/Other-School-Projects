package eu.glowacki.utp.assignment02.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eu.glowacki.utp.assignment02.HumanResourcesStatistics;
import eu.glowacki.utp.assignment02.employee.Employee;
import eu.glowacki.utp.assignment02.employee.Manager;
import eu.glowacki.utp.assignment02.employee.Trainee;
import eu.glowacki.utp.assignment02.employee.Worker;

public class HumanResourcesStatisticsTest {

	private List<Employee> _allEmployees; // all employees ---  i.e. Workers, Trainees and their Managers and top Director (also an instance of Manager class)
	
	
	@Before
	public void before() {
		_allEmployees = new ArrayList<>();
		Manager director = new Manager("Director", "Smith", new GregorianCalendar(1970, Calendar.JANUARY, 10).getTime(), 
				new BigDecimal(20000), null, new GregorianCalendar(1999, Calendar.JANUARY, 8).getTime(), 
				new BigDecimal(2000));
		
		/////////////////////
		Manager manager1 = new Manager("Manager1", "Smith", new GregorianCalendar(1973, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(8000), director, new GregorianCalendar(2000, Calendar.MARCH, 2).getTime(), 
				new BigDecimal(1000));
		
		Worker worker1 = new Worker("Worker1", "Avon", new GregorianCalendar(1982, Calendar.JUNE, 7).getTime(), 
				new BigDecimal(5000), manager1, new GregorianCalendar(1999, Calendar.JANUARY, 10).getTime(),
				new BigDecimal(500));
		
		Worker worker2 = new Worker("Worker2", "XAvon", new GregorianCalendar(1975, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(5000), manager1, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(500));
		
		Worker worker3 = new Worker("Worker3", "XAvon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(5000), manager1, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(500));
		
		Trainee trainee1 = new Trainee("Trainee1", "XAvon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(3000), manager1, new GregorianCalendar(1999, Calendar.MARCH, 7).getTime(),
				100);
		
		
		////////////////
		Manager manager2 = new Manager("Manager2", "Brown", new GregorianCalendar(1975, Calendar.NOVEMBER, 5).getTime(), 
				new BigDecimal(10500), director, new GregorianCalendar(2003, Calendar.APRIL, 2).getTime(), 
				new BigDecimal(1000));
		
		Worker worker4 = new Worker("Worker4", "Avon", new GregorianCalendar(1982, Calendar.JUNE, 7).getTime(), 
				new BigDecimal(4500), manager2, new GregorianCalendar(1999, Calendar.JANUARY, 10).getTime(),
				new BigDecimal(500));
		
		Worker worker5 = new Worker("Worker5", "XAvon", new GregorianCalendar(1975, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(7000), manager2, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(500));
		
		Worker worker6 = new Worker("Worker6", "XAvon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(5000), manager2, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(500));
		
		Trainee trainee2 = new Trainee("Trainee2", "XAvon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(3500), manager2, new GregorianCalendar(1999, Calendar.MARCH, 7).getTime(),
				100);
		
		
		
		////////////////
		Manager manager3 = new Manager("Manager3", "Smith", new GregorianCalendar(1978, Calendar.SEPTEMBER, 15).getTime(), 
				new BigDecimal(12000), director, new GregorianCalendar(2004, Calendar.JULY, 22).getTime(), 
				new BigDecimal(2000));
		
		Worker worker7 = new Worker("Worker7", "Avon", new GregorianCalendar(1982, Calendar.JUNE, 7).getTime(), 
				new BigDecimal(5000), manager3, new GregorianCalendar(1999, Calendar.JANUARY, 10).getTime(),
				new BigDecimal(500));
		
		Worker worker8 = new Worker("Worker8", "Avon", new GregorianCalendar(1975, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(4000), manager3, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(500));
		
		Worker worker9 = new Worker("Worker9", "Avon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(5000), manager3, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
				new BigDecimal(100));
		
		Trainee trainee3 = new Trainee("Trainee3", "Avon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
				new BigDecimal(3000), manager3, new GregorianCalendar(1999, Calendar.MARCH, 7).getTime(),
				100);
		
		
		/////////////////////
		Manager manager4 = new Manager("Manager4", "Smith", new GregorianCalendar(1973, Calendar.DECEMBER, 7).getTime(), 
			new BigDecimal(9000), director, new GregorianCalendar(2000, Calendar.MARCH, 2).getTime(), 
			new BigDecimal(1000));
		
		Worker worker10 = new Worker("Worker10", "Avon", new GregorianCalendar(1982, Calendar.JUNE, 7).getTime(), 
			new BigDecimal(5000), manager4, new GregorianCalendar(1999, Calendar.JANUARY, 10).getTime(),
			new BigDecimal(500));
		
		Worker worker11 = new Worker("Worker11", "Avon", new GregorianCalendar(1975, Calendar.DECEMBER, 7).getTime(), 
			new BigDecimal(5000), manager4, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
			new BigDecimal(500));
		
		Worker worker12 = new Worker("Worker12", "Avon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
			new BigDecimal(5000), manager4, new GregorianCalendar(1999, Calendar.FEBRUARY, 7).getTime(),
			new BigDecimal(500));
		
		Trainee trainee4 = new Trainee("Trainee4", "Avon", new GregorianCalendar(1981, Calendar.DECEMBER, 7).getTime(), 
			new BigDecimal(3000), manager4, new GregorianCalendar(1999, Calendar.MARCH, 7).getTime(),
			100);
		
		manager2.set_subordinates(Arrays.asList(worker4, worker5, worker6, trainee2));
		manager1.set_subordinates(Arrays.asList(worker1, worker2, worker3, trainee1, manager2));
		manager3.set_subordinates(Arrays.asList(worker7, worker8, worker9, trainee3));
		manager4.set_subordinates(Arrays.asList(worker10, worker11, worker12, trainee4));
		
		director.set_subordinates(Arrays.asList(manager1, manager2, manager3, manager4));
		/////////////////////
		
		_allEmployees = Arrays.asList(director, manager1, manager2, manager3, manager4,
				worker1, worker2, worker3, worker4, worker5, worker6, 
				worker7, worker8, worker9, worker10, worker11, worker12,
				trainee1, trainee2, trainee3, trainee4
				);
	}
	
	@Test 
	public void olderThanAndEarnMore() {
		List<Employee> expected = Arrays.asList(_allEmployees.get(0));
		List<Employee> actual = HumanResourcesStatistics.olderThanAndEarnMore(_allEmployees, _allEmployees.get(3));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void practiceLengthLongerThan() {
		List<Trainee> actual = HumanResourcesStatistics.practiceLengthLongerThan(_allEmployees, 110);
		
		Trainee t = (Trainee)_allEmployees.get(17);
		List<Trainee> expected = Arrays.asList(t);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test 
	public void seniorityLongerThan() {
		List<Worker> actual = HumanResourcesStatistics.seniorityLongerThan(_allEmployees, 19 * 2 + 8);
		
		Worker w = (Worker)_allEmployees.get(0);
		List<Worker> expected = Arrays.asList(w);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test 
	public void seniorityBetweenOneAndThreeYears() {
		List<Worker> actual = HumanResourcesStatistics.seniorityBetweenOneAndThreeYears(_allEmployees);
		
		Worker w = (Worker)_allEmployees.get(5);
		List<Worker> expected = Arrays.asList(w);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test 
	public void seniorityLongerThanButEarnLess() {
		List<Worker> actual = HumanResourcesStatistics.seniorityLongerThanButEarnLess(_allEmployees, _allEmployees.get(4));
		
		Worker w = (Worker)_allEmployees.get(1);
		List<Worker> expected = Arrays.asList(w);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test 
	public void seniorityBetweenTwoAndFourYearsAndAgeGreaterThan() {
		List<Worker> actual = HumanResourcesStatistics.seniorityBetweenTwoAndFourYearsAndAgeGreaterThan(_allEmployees, 30);
		
		Worker w = (Worker)_allEmployees.get(5);
		List<Worker> expected = Arrays.asList(w);
		
		Assert.assertEquals(expected, actual);
	}
	
}