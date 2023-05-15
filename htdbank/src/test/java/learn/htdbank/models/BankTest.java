package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    @Test
    void nullBankShouldFailValidation() {
        Bank bank = new Bank();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Bank>> violations = validator.validate(bank);

        assertEquals(1, violations.size());
    }

    @Test
    void numbersLessThanOneShouldFailValidation() {
        Bank bank = new Bank();
        bank.setRouting_number(0);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Bank>> violations = validator.validate(bank);

        assertEquals(1, violations.size());
    }
}