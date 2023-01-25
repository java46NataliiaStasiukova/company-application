package telran.company.dto;

import jakarta.validation.constraints.*;

public class Employee {
	@Null
	private Integer id;
	@Pattern(regexp = "[A-Z][a-z]*") @NotEmpty
	public String firstName;
	@Pattern(regexp = "[A-Z][a-z]*") @NotEmpty
	public String lastName;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") @NotEmpty
	public String birthDate;
	@Min(5000) @Max(45000) @NotNull
	public Integer salary;
	
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
