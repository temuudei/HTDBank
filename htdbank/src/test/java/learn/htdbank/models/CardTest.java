package learn.htdbank.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void nullCardShouldFailValidation() {
        Card card = new Card();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Card>> violations = validator.validate(card);

        assertEquals(1, violations.size());
    }

    @Test
    void nullTypeShouldFailValidation() {
        Card card = new Card();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Card>> violations = validator.validate(card);

        assertEquals(1, violations.size());
    }

    @Test
    void numbersGreaterThanTenShouldFailValidation() {
        Card card = new Card();
        card.setType("testtesttesttest");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Card>> violations = validator.validate(card);

        assertEquals(1, violations.size());
    }
}