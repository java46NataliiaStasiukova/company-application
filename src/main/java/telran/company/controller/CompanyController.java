package telran.company.controller;

import java.security.Principal;
import java.time.Month;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import telran.company.dto.Employee;
import telran.company.service.CompanyService;

@RestController
@RequestMapping("company")
@Validated
public class CompanyController {
private static Logger LOG = LoggerFactory.getLogger(CompanyController.class);
CompanyService companyService;

public CompanyController(CompanyService companyService) {
	this.companyService = companyService;
}

@PostMapping("/employees")
String addEmployee(@RequestBody @Valid Employee employee, Principal user) {
	LOG.info("request for adding employee: {}, by user: {}", employee.toString(), user.getName());
	companyService.addEmployee(employee);
	return String.format("employee %s has been added", employee.toString());
}

@PutMapping("/employees")
String updateEmployee(@RequestBody @Valid Employee employee, Principal user) {
	LOG.info("request for uodating employee: {}, by user: {}", employee.toString(), user.getName());
	companyService.updateEmployee(employee);
	return String.format("employee %s has been updated", employee.toString());
}


@DeleteMapping("/employees/{id}")
String deleteEmployee(@PathVariable Integer id, Principal user) {
	LOG.debug("request for deleting employee with id: {}, by user: {}", id, user.getName());
	Employee empl = companyService.deleteEmployee(id);
	return String.format("employee %s has been deleted", empl.toString());
}

@GetMapping("/employees/salary/{salaryFrom}/{salaryTo}")	
String getEmployeeBySalary(@PathVariable @Min(6000) int salaryFrom,@Max(45000) @PathVariable int salaryTo, Principal user) {
	LOG.debug("request for getting employee with salary in range: {}-{}, by user: {}", salaryFrom, salaryTo, user.getName());
	String res = String.format("employee in salary range [%s-%s] was not found", 
	salaryFrom, salaryTo);
	List<Employee> list = companyService.employeesBySalary(salaryFrom, salaryTo);
	if(list.size() != 0) {
		res = String.format("found: %s employees with salary range[%s-%s]: %s",
				list.size(), salaryFrom, salaryTo, list.toString());
	}
	return res;
}

@GetMapping("/employees/age/{ageFrom}/{ageTo}")	
String getEmployeeByAge(@PathVariable @Min(20) int ageFrom,@Max(70) @PathVariable int ageTo, Principal user) {
	LOG.debug("request for getting employee with age in range: {}-{}, by user: {}", ageFrom, ageTo, user.getName());
	String res = String.format("employees with age range [%s-%s] was not found",
			ageFrom, ageTo);
	List<Employee> list = companyService.employeesByAge(ageFrom, ageTo);
	if(list.size() != 0) {
		res = String.format("found: %s employees in age range [%s-%s]: %s",
				list.size(), ageFrom, ageTo, list.toString());
	}
	return res;
}

@GetMapping("employees/month/{month}")
String getEmployeeByMonthBirth(@PathVariable @Min(1) @Max(12)int month, Principal user) {
	LOG.debug("request for getting employees with birthday on month: {}, by user: {}", Month.of(month), user.getName());
	String res = String.format("employees with month of birth: %s was not found", Month.of(month));
	List<Employee> list = companyService.employeesByBirthMonth(month);
	if(list.size() != 0) {
		res = String.format("found: %s employees with birth month %s: %s", list.size(), Month.of(month),
				list.toString());
	} 
	return res;
}

@PreDestroy
void shutdown() {
	
	LOG.info("shutdown performed");
}



	
	
	
	
	
}
