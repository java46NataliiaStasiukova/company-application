package telran.company;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.company.dto.Employee;
import telran.company.service.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = {"ADMIN_PASSWORD=admin-pass"})
class CompanyServiceTest {
static Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);
@Autowired
CompanyService companyService;
static int id;
static Employee employee;
	
	@Test
	@Order(1)
	void addEmployee() {
		employee = new Employee();
		employee.firstName = "Sara";
		employee.lastName = "Jons";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		assertTrue(companyService.addEmployee(employee).equals(employee));
		id = employee.getId();
	}

	@Test
	@Order(2)
	void updateEmployee() {
		Employee empl = new Employee();
		empl.setId(id);
		empl.firstName = "Itzahak";
		empl.lastName = "Cohen";
		empl.birthDate = "1999-09-09";
		empl.salary = 25000;
		employee = companyService.updateEmployee(empl);
		assertTrue(employee.equals(empl));
	}
	
	@Test
	@Order(3)
	void getEmployeeSalary() {
		List<Employee> test = companyService.employeesBySalary(24000, 26000);
		List<Employee> expected = new ArrayList<>();
		expected.add(employee);
		assertEquals(expected, test);
		
		test = companyService.employeesBySalary(35000, 40000);
		expected = new ArrayList<>();
		assertEquals(expected, test);
		assertEquals(0, test.size());
		
		assertThrows(IllegalArgumentException.class, () -> {;
		companyService.employeesBySalary(12000, 10000);
	});
	}
	
	@Test
	@Order(4)
	void getEmployeesAge() {
		List<Employee> test = companyService.employeesByAge(20, 25);
		List<Employee> expected = new ArrayList<>();
		expected.add(employee);
		assertEquals(expected, test);
		
		test = companyService.employeesByAge(35, 40);
		expected = new ArrayList<>();
		assertEquals(expected, test);
		assertEquals(0, test.size());
		
		assertThrows(IllegalArgumentException.class, () -> {;
		companyService.employeesByAge(25, 20);
	});
	}
	
	@Test
	@Order(5)
	void getEmployeeByBirthMonth() {
		List<Employee> test = companyService.employeesByBirthMonth(9);
		List<Employee> expected = new ArrayList<>();
		expected.add(employee);
		assertEquals(expected, test);
		
		test = companyService.employeesByBirthMonth(5);
		expected = new ArrayList<>();
		assertEquals(expected, test);
		assertEquals(0, test.size());	
	}
	
	@Test
	@Order(6)
	void deleteEmployee() {
		Employee expected = employee;
		assertEquals(expected, companyService.deleteEmployee(id));
		
		assertThrows(NoSuchElementException.class, () -> {;
		companyService.deleteEmployee(id);
		});
	}
	
	@Test
	@Order(7)
	void updateEmployeeNotExists() {
		Employee empl = new Employee();
		empl.setId(id);
		empl.firstName = "Vasya";
		empl.lastName = "Petrov";
		empl.birthDate = "1985-01-12";
		empl.salary = 6000;
		
		assertThrows(NoSuchElementException.class, () -> {;
			companyService.updateEmployee(empl);
		});
	}

}
