package telran.company.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import telran.company.dto.Employee;

@Service
public class CompanyServiceImpl implements CompanyService {
	private static final int MIN = 100000000;
	private static final int MAX = 999999999;
	private static Logger LOG = LoggerFactory.getLogger(CompanyService.class);
	private HashMap<Integer, Employee> employees;
	private TreeMap<Integer, Set<Employee>> employeesSalary;
	private TreeMap<Integer, Set<Employee>> employeesAge;
	private HashMap<Integer, Set<Employee>> employeesBirthMonth;

	public CompanyServiceImpl(HashMap<Integer, Employee> employees, TreeMap<Integer, Set<Employee>> employeesSalary,
			TreeMap<Integer, Set<Employee>> employeesAge, HashMap<Integer, Set<Employee>> employeesBirthMonth) {
		this.employees = employees;
		this.employeesSalary = employeesSalary;
		this.employeesAge = employeesAge;
		this.employeesBirthMonth = employeesBirthMonth;
	}
	


	@Override
	public Employee addEmployee(Employee empl) {
		Integer id = generateId();
		empl.setId(id);	

		return add(id, empl);
	}

	private Employee add(Integer id, Employee empl) {
		employees.put(id, empl);
		employeesSalary.computeIfAbsent(empl.salary, k -> new HashSet<Employee>()).add(empl);
		employeesAge.computeIfAbsent((int) ChronoUnit.YEARS.between(LocalDate.parse(empl.birthDate), LocalDate.now()),
				k -> new HashSet<Employee>()).add(empl);
		employeesBirthMonth.computeIfAbsent(LocalDate.parse(empl.birthDate).getMonthValue(),
				k -> new HashSet<Employee>()).add(empl);
		return empl;
	}



	private int generateId() {
		
		return ThreadLocalRandom.current().nextInt(MIN, MAX);
	}

	@Override
	public Employee updateEmployee(Employee empl) {
		if(employees.get(empl.getId()) == null) {
			throw new NoSuchElementException(String.format("employee with id: %s was not found", empl.getId()));
		}
		deleteEmployee(empl.getId());
		
		return add(empl.getId(), empl);
	}

	@Override
	public Employee deleteEmployee(int id) {
		Employee employee = employees.remove(id);
		if(employee == null) {
			throw new NoSuchElementException(String.format("employee with id: %s was not found", id));
		}
		employeesSalary.get(employee.salary).remove(employee);
		employeesAge.get((int) ChronoUnit.YEARS.between(LocalDate.parse(employee.birthDate),
				LocalDate.now())).remove(employee);
		employeesBirthMonth.get(LocalDate.parse(employee.birthDate).getMonthValue()).remove(employee);
		
		return employee;
	}

	@Override
	public List<Employee> employeesBySalary(int salaryFrom, int salaryTo) {
		if(salaryFrom > salaryTo) {
			throw new IllegalArgumentException(String.format("salary from: %s can not be bigger then salary to: %s", salaryFrom, salaryTo));
		}
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true)
				.entrySet()
				.stream().flatMap(x -> x.getValue().stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Employee> employeesByAge(int ageFrom, int ageTo) {
		if(ageFrom > ageTo) {
			throw new IllegalArgumentException(String.format("age from: %s can not be bigger then age to: %s", ageFrom, ageTo));
		}
		return employeesAge.subMap(ageFrom, true, ageTo, true)
				.entrySet()
				.stream().flatMap(x -> x.getValue().stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Employee> employeesByBirthMonth(int monthNumber) {

		return employeesBirthMonth.getOrDefault(monthNumber, Collections.emptySet())
				.stream().collect(Collectors.toList());
	}

}
