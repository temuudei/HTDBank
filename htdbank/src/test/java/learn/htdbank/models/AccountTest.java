package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void nullAccountShouldFailValidation() {
        Account account = new Account();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(1, violations.size());
    }

    @Test
    void nullBalanceShouldFailValidation() {
        Account account = new Account();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(1, violations.size());
    }

    @Test
    void negativeBalanceShouldFailValidation() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(-1));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        assertEquals(1, violations.size());
    }
}