package learn.htdbank.data;

import learn.htdbank.models.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BankJdbcTemplateRepositoryTest {
    @Autowired
    BankJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;
    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Bank> banks = repository.findAll();
        assertNotNull(banks);
    }

    @Test
    void shouldFindById() {
        Bank bank = new Bank();
        bank.setBank_id(1);
        bank.setRouting_number(999999999);

        Bank actual = repository.findById(1);
        assertEquals(bank.getBank_id(), actual.getBank_id());
        assertEquals(bank.getRouting_number(), actual.getRouting_number());
    }

    @Test
    void shouldAdd() {
        Bank bank = makeBank();
        Bank actual = repository.add(bank);
        assertNotNull(actual);
        assertEquals(3, actual.getBank_id());
    }

    @Test
    void shouldUpdate() {
        Bank bank = makeBank();
        bank.setBank_id(1);
        assertTrue(repository.update(bank));
        bank.setBank_id(50);
        assertFalse(repository.update(bank));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    private Bank makeBank() {
        Bank bank = new Bank();
        bank.setRouting_number(9898989);
        return bank;
    }
}