package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    @Test
    void nullTransactionShouldFailValidation() {
        Transaction transaction = new Transaction();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertEquals(2, violations.size());
    }

    @Test
    void emptyTransactionTypeShouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(5));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertEquals(1, violations.size());
    }

    @Test
    void transactionTypeCharactersLongerThanTenShouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setTransaction_type("testetstetstestes");
        transaction.setAmount(BigDecimal.valueOf(5));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertEquals(1, violations.size());
    }

    @Test
    void emptyAmountShouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setTransaction_type("deposit");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertEquals(1, violations.size());
    }

    @Test
    void amountLessThanZeroShouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setTransaction_type("deposit");
        transaction.setAmount(BigDecimal.valueOf(-1));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertEquals(1, violations.size());
    }
}