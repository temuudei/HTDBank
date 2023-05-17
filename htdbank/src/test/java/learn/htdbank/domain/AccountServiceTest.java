package learn.htdbank.domain;

import learn.htdbank.data.AccountRepository;
import learn.htdbank.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class AccountServiceTest {
    @Autowired
    AccountService service;
    @MockBean
    AccountRepository repository;
    @Test
    void shouldFindAll() {
        List<Account> accounts = repository.findAll();
        assertNotNull(accounts);
    }

    @Test
    void shouldFindById() {
        Account expected = new Account();
        expected.setAccount_id(1);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(547.34));

        when(repository.findById(1)).thenReturn(expected);
        Account actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Account account = new Account();
        account.setCustomer_id(1);
        account.setBank_id(1);
        account.setBalance(BigDecimal.valueOf(23.32));
        Result<Account> result = service.add(account);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldUpdate() {
        Account account = new Account();
        account.setAccount_id(1);
        account.setCustomer_id(1);
        account.setBank_id(1);
        account.setBalance(BigDecimal.valueOf(23.32));

        when(repository.update(account)).thenReturn(true);

        Result<Account> result = service.update(account);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldDeleteById() {
        Account account = new Account();
        account.setAccount_id(1);
        account.setCustomer_id(1);
        account.setBank_id(1);
        account.setBalance(BigDecimal.valueOf(23.23));
        when(repository.deleteById(account.getAccount_id())).thenReturn(true);
    }


    @Test
    void shouldFailValidationWhenBalanceIsNull() {
        Account expected = new Account();
        expected.setAccount_id(0);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        Result<Account> result = service.add(expected);
        assertNotEquals(0, result.getErrorMessages().size());

        expected.setBalance(BigDecimal.valueOf(232.23));
        result = service.add(expected);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenBalanceIsNegative() {
        Account expected = new Account();
        expected.setAccount_id(0);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(-1));
        Result<Account> result = service.add(expected);
        assertNotEquals(0, result.getErrorMessages().size());
    }
    @Test
    void shouldFailValidationWhenIdIsNotZeroForAdd() {
        Account expected = new Account();
        expected.setAccount_id(1);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(34.34));
        Result<Account> result = service.add(expected);
        assertNotEquals(0, result.getErrorMessages().size());
    }
    @Test
    void shouldFailValidationWhenIdIsNotSet() {
        Account expected = new Account();
        expected.setAccount_id(0);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(34.34));
        Result<Account> result = service.update(expected);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNotFoundInUpdate() {
        Account expected = new Account();
        expected.setAccount_id(8);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(347.23));
        Result<Account> result = service.update(expected);
        assertNotEquals(0, result.getErrorMessages().size());
    }
}