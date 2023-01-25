package telran.company.service;

import java.util.List;

import telran.company.dto.Employee;

//CompanyService interface containing the method declarations 
//of functional part of the application

public interface CompanyService {

	Employee addEmployee(Employee empl);
	
	Employee updateEmployee(Employee empl);
	
	Employee deleteEmployee(int id);
	
	List<Employee> employeesBySalary(int salaryFrom, int salaryTo);
	
	List<Employee> employeesByAge(int ageFrom, int ageTo);
	
	List<Employee> employeesByBirthMonth(int monthNumber);
	
	
}
