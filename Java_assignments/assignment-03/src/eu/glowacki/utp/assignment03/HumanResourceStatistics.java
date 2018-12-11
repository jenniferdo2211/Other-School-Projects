package eu.glowacki.utp.assignment03;

import java.util.List;

import eu.glowacki.utp.assignment03.employee.Employee;
import eu.glowacki.utp.assignment03.employee.Trainee;
import eu.glowacki.utp.assignment03.employee.Worker;

public final class HumanResourceStatistics {

	// The best solution is to impleent the below features as static methods having a list of Employee as the first input argument.
	// In addition the list of arguments may be augmented with parameters required for the given feature.
	// najlepiej zaimplementowaæ poni¿sze metody jako statyczne, gdzie argumentem lista pracowników i odpowiednio dodatkowo to co wymagane w danym punkcie
	
	// (assignment 03)
	// methods:
	//
	// * search for Employees older than given employee and earning less than him
	//   wyszukaj osoby zatrudnione (Employee), które s¹ starsze od podanej innej zatrudnionej osoby oraz zarabiaj¹ mniej od niej
	public static List<Employee> olderThenAndEarnMore(List<Employee> allEmployees, Employee employee) {
		return null;
	}
	
	//
	// * search for Trainees whose practice length is longer than given number of days and raise their salary by 5%
	//   wyszukaj praktykantów (Trainee), których praktyka jest d³u¿sza od podanej liczby dni,
	//   a nastêpnie podnieœ ich uposa¿enie o 5%
	public static List<Trainee> practiceLengthLongerThan(List<Employee> allEmployees, int daysCount) {
		return null;
	}
	
	//
	// * search for Workers whose seniority is longer than given number of months and give them bonus of 300 if their bonus is smaller
	//   wyszukaj pracowników o sta¿u d³u¿szym ni¿ podana liczba miesiêcy,
	//   a nastêpnie przyznaj im premiê w wysokoœci 300 jeœli ich premia jest ni¿sza
	public static List<Worker> seniorityLongerThan(List<Employee> allEmployees, int monthCount) {
		return null;
	}
	
	//
	// * search for Workers whose seniority is between 1 and 3 years and give them raise of salary by 10%
	//   wyszukaj pracowników o sta¿u pomiêdzy 1 a 3 lata i przyznaj im podwy¿kê w wysokoœci 10%
	public static List<Worker> seniorityBetweenOneAndThreeYears(List<Employee> allEmployees) {
		return null;
	}
	
	//
	// * search for Workers whose seniority is longer than the seniority of a given employee and earn less than him and align their salary with the given employee
	//   wyszukaj pracowników o sta¿u d³u¿szym ni¿ sta¿ podanego pracownika i którzy zarabiaj¹ mniej od niego,
	//   nastêpnie zrównaj ich wynagrodzenie z wynagrodzeniem danego pracownika
	public static List<Worker> seniorityLongerThan(List<Employee> allEmployees, Employee employee) {
		return null;
	}
	
	//
	// * search for Workers whose seniority is between 2 and 4 years and whose age is greater than given number of years
	//   wyszukaj pracowników o sta¿u pomiêdzy 2 i 4 lata i starszych ni¿ podana liczba lat
	public static List<Worker> seniorityBetweenTwoAndFourYearsAndAgeGreaterThan(List<Employee> allEmployees, int age) {
		return null;
	}
}