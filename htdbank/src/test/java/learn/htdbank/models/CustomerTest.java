package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void nullCustomerShouldFailValidation() {
        Customer customer = new Customer();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertEquals(3, violations.size());
    }

    @Test
    void nullFirstNameShouldFailValidation(){
        Customer customer = new Customer();
        customer.setLast_name("Last name");
        customer.setSsn(5);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertEquals(1, violations.size());
    }

    @Test
    void nullLastNameShouldFailValidation(){
        Customer customer = new Customer();
        customer.setFirst_name("First name");
        customer.setSsn(5);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertEquals(1, violations.size());
    }

    @Test
    void ssnLessThanOneShouldFailValidation() {
        Customer customer = new Customer();
        customer.setFirst_name("First name");
        customer.setLast_name("Last name");
        customer.setSsn(0);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertEquals(1, violations.size());
    }
}