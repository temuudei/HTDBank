package learn.htdbank.domain;

import learn.htdbank.data.BankRepository;
import learn.htdbank.models.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BankServiceTest {
    @Autowired
    BankService service;
    @MockBean
    BankRepository repository;
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

        when(repository.findById(1)).thenReturn(bank);
        Bank actual = service.findById(1);
        assertEquals(bank, actual);
    }

    @Test
    void shouldAdd() {
        Bank bank = new Bank();
        bank.setRouting_number(999999999);
        Result<Bank> result = service.add(bank);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldUpdate() {
        Bank bank = new Bank();
        bank.setBank_id(1);
        bank.setRouting_number(999999999);
        when(repository.update(bank)).thenReturn(true);

        Result<Bank> result = service.update(bank);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldDeleteById() {
        Bank bank = new Bank();
        bank.setBank_id(1);
        bank.setRouting_number(999999999);
        when(repository.deleteById(bank.getBank_id())).thenReturn(true);
    }

    @Test
    void shouldFailValidationWhenBankIsNull() {
        Bank bank = new Bank();
        Result<Bank> result = service.add(bank);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNegativeForAdd() {
        Bank bank = new Bank();
        bank.setRouting_number(-1);
        Result<Bank> result = service.add(bank);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNotZeroForAdd() {
        Bank bank = new Bank();
        bank.setBank_id(1);
        bank.setRouting_number(999999999);
        Result<Bank> result = service.add(bank);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNegativeForUpdate() {
        Bank bank = new Bank();
        bank.setBank_id(-1);
        bank.setRouting_number(999999999);
        Result<Bank> result = service.update(bank);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNotFoundForUpdate() {
        Bank bank = new Bank();
        bank.setBank_id(8);
        bank.setRouting_number(999999999);
        Result<Bank> result = service.update(bank);
        assertNotEquals(0, result.getErrorMessages().size());
    }
}