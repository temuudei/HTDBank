package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void nullEmployeeShouldFailValidation() {
        Employee employee = new Employee();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(3, violations.size());
    }

    @Test
    void nullFirstNameShouldFailValidation() {
        Employee employee = new Employee();
        employee.setLast_name("Last name");
        employee.setSalary(BigDecimal.valueOf(5));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLastNameShouldFailValidation() {
        Employee employee = new Employee();
        employee.setFirst_name("First name");
        employee.setSalary(BigDecimal.valueOf(5));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(1, violations.size());
    }

    @Test
    void nullSalaryShouldFailValidation() {
        Employee employee = new Employee();
        employee.setFirst_name("First name");
        employee.setLast_name("Last name");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(1, violations.size());
    }

    @Test
    void salaryAmountLessThanOneShouldFailValidation() {
        Employee employee = new Employee();
        employee.setFirst_name("First name");
        employee.setLast_name("Last name");
        employee.setSalary(BigDecimal.valueOf(0));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        assertEquals(1, violations.size());
    }
}