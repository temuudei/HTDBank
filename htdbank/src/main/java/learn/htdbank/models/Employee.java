package learn.htdbank.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Employee {
    private int employee_id;
    @NotBlank(message = "First name is required.")
    @Size(max = 50, message = "First name cannot be greater than 50 characters.")
    private String first_name;
    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot be greater than 50 characters.")
    private String last_name;
    @NotNull(message = "Salary amount is required")
    @Min(value = 1, message = "Salary amount has to be at least 1.")
    private BigDecimal salary;
    private int bank_id;
    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
}
