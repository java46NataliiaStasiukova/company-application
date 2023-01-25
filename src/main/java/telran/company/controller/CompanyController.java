package telran.company.controller;

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
String addEmployee(@RequestBody @Valid Employee employee) {
	LOG.debug("request for adding employee: {}", employee.toString());
	String res = String.format("employee with username %s can not be added", employee.firstName);
	if(companyService.addEmployee(employee).getId() != null) {
		res = String.format("employee %s has been added", employee.toString());
		LOG.debug("employee: {} was added", employee.toString());
	} else {
		LOG.warn("employee: {} was NOT added", employee.toString());
	}
	return res;
}

@PutMapping("/employees")
String updateEmployee(@RequestBody @Valid Employee employee) {
	LOG.debug("request for uodating employee: {}", employee.toString());
	String res = String.format("employee with id %s doesn't exist", employee.getId());
	if(companyService.updateEmployee(employee) != null) {
		res = String.format("employee %s has been updated", employee.toString());
		LOG.debug("employee: {} was updated", employee.toString());
	} else {
		LOG.warn("employee: {} was NOT updated", employee.toString());
	}
	return res;
}


@DeleteMapping("/employees/{id}")
String deleteEmployee(@PathVariable Integer id) {
	LOG.debug("request for deleting employee with id: {}", id);
	String res = String.format("employee with id %s doesn't exist", id);
	Employee employee = companyService.deleteEmployee(id);
	if (employee != null) {
		res = String.format("employee %s has been deleted", employee.toString());
		LOG.info("remployee: {} was deleted", employee.toString());
	}
	return res;
}

@GetMapping("/employees/salary/{salaryFrom}/{salaryTo}")	
String getEmployeeBySalary(@PathVariable @Min(6000) int salaryFrom,@Max(45000) @PathVariable int salaryTo) {
	LOG.debug("request for getting employee with salary in range: {}-{}", salaryFrom, salaryTo);
	String res = String.format("employee with salary range [%s-%s] was not found", 
	salaryFrom, salaryTo);
	List<Employee> list = companyService.employeesBySalary(salaryFrom, salaryTo);
	
	if(list.size() != 0) {
		res = String.format("list employee by salaty in range[%s-%s]: %s",
		salaryFrom, salaryTo, list.toString());
		LOG.debug("there are {} employees in salary range: {}-{}", list.size(), salaryFrom, salaryTo);
	} else {
		LOG.warn("employees with salary range: {}-{} was not found", salaryFrom, salaryTo);
	}
	return res;
}

@GetMapping("/employees/age/{ageFrom}/{ageTo}")	
String getEmployeeByAge(@PathVariable @Min(20) int ageFrom,@Max(70) @PathVariable int ageTo) {
	LOG.debug("request for getting employee with age in range: {}-{}", ageFrom, ageTo);
	String res = String.format("employees with age range [%s-%s] was not found",
			ageFrom, ageTo);
	List<Employee> list = companyService.employeesByAge(ageFrom, ageTo);
	if(list.size() != 0) {
		res = String.format("list employees by age in range [%s-%s]: %s",
				ageFrom, ageTo, list.toString());
		LOG.debug("there are {} employees in age range: {}-{}", list.size(), ageFrom, ageTo);
	} else {
		LOG.warn("employees with age range: {}-{} was not found", ageFrom, ageTo);
	}
	return res;
}

@GetMapping("employees/month/{month}")
String getEmployeeByMonthBirth(@PathVariable @Min(1) @Max(12)int month) {
	LOG.debug("request for getting employee with birthday on month: {}", Month.of(month));
	String res = String.format("employees with month of birth: %s was not found", Month.of(month));
	List<Employee> list = companyService.employeesByBirthMonth(month);
	if(list.size() != 0) {
		res = String.format("list employees with birth month %s: %s", Month.of(month),
				list.toString());
		LOG.debug("there are {} employees with birthday on month: {}", list.size(), Month.of(month));
	} else {
		LOG.warn("employees with wuth birthday in month: {} was not found", Month.of(month));
	}
	return res;
}

@PreDestroy
void shutdown() {
	
	LOG.info("shutdown performed");
}



	
	
	
	
	
}
