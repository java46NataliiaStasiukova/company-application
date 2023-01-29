package telran.company;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.company.controller.CompanyController;
import telran.company.dto.Employee;
import telran.company.service.CompanyService;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {
	@MockBean
	CompanyService companyService;
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser(roles = "ADMIN")
	void addEmployeeNormalTest() throws Exception {
		Employee employee = new Employee();
		employee.firstName = "Sara";
		employee.lastName = "Herzel";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		String employeeJSON = mapper.writeValueAsString(employee);
		mockMvc.perform(post("http://localhost/company/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void addEmployeeBadTest() throws Exception {
		Employee employee = new Employee();
		employee.firstName = "sara";
		employee.lastName = "Herzel";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		String employeeJSON = mapper.writeValueAsString(employee);
		mockMvc.perform(post("http://localhost/company/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void addEmployeeIsForbidden() throws Exception {
		Employee employee = new Employee();
		employee.firstName = "Sara";
		employee.lastName = "Herzel";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		String employeeJSON = mapper.writeValueAsString(employee);
		mockMvc.perform(post("http://localhost/company/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJSON))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN_COMPANY")
	void deleteEmployeeBadRequestTest() throws Exception {
		mockMvc.perform(delete("http://localhost/company/employees/{}"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN_COMPANY")
	void updateEmployeeTest() throws Exception {
		Employee employee = new Employee();
		employee.firstName = "Sara";
		employee.lastName = "Herzel";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		String employeeJSON = mapper.writeValueAsString(employee);
		mockMvc.perform(put("http://localhost/company/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void updateEmployeeBadTest() throws Exception {
		Employee employee = new Employee();
		employee.firstName = "Sara";
		employee.lastName = "Herzel";
		employee.birthDate = "1987-03-03";
		employee.salary = 10000;
		String employeeJSON = mapper.writeValueAsString(employee);
		mockMvc.perform(put("http://localhost/company/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJSON))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "ACCOUNTER")
	void getEmployeeBySalaryTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/salary/6000/7000"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void getEmployeeBySalaryBadTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/salary/6000/7000"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void getEmployeeByAgeTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/age/20/25"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void getEmployeeByAgeBadTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/age/18/25"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void getEmployeeByMonthBadTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/month/25"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void getEmployeeByMonthTest() throws Exception {
		mockMvc.perform(get("http://localhost/company/employees/month/12"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	

}
