package telran.company.dto;

import jakarta.validation.constraints.*;

public class Employee {
	@Positive
	@Digits(fraction = 0, integer = 9, message ="id shuld contains 9 digits")
	private Integer id;
	@NotEmpty(message = "name cannot be empty") @Pattern(regexp = "[A-Z][a-z]*", message = "First leter should be capital; name should not contains numbers") 
	public String firstName;
	@NotEmpty(message = "name cannot be empty") @Pattern(regexp = "[A-Z][a-z]*", message = "First leter should be capital; name should not contains numbers") 
	public String lastName;
	@NotEmpty(message = "birth date cannot be empty") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = ("format required: yyyy-mm-dd")) 
	public String birthDate;
	@Min(5000) @Max(45000) @NotNull(message = "salary field can not be empty")
	public int salary;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	@Override
	public String toString() {
		
		return String.format("id: %s, first name: %s, last name: %s, birth date: %s, salary: %s",
				id, firstName, lastName, birthDate, salary);
	}

}
