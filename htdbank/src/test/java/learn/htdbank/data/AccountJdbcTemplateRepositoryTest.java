package learn.htdbank.data;

import learn.htdbank.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AccountJdbcTemplateRepositoryTest {
    @Autowired
    AccountJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;
    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Account> accounts = repository.findAll();
        assertNotNull(accounts);
    }

    @Test
    void shouldFindById() {
        Account account = new Account();
        account.setAccount_id(1);
        account.setBalance(BigDecimal.valueOf(547.34));
        account.setBank_id(1);
        account.setCustomer_id(1);

        Account actual = repository.findById(1);
        assertEquals(account.getBalance(), actual.getBalance());
        assertEquals(account.getAccount_id(), actual.getAccount_id());
    }

    @Test
    void shouldAdd() {
        Account account = makeAccount();
        Account actual = repository.add(account);
        assertNotNull(actual);
        assertEquals(3, actual.getAccount_id());
    }

    @Test
    void shouldUpdate() {
        Account account = makeAccount();
        account.setAccount_id(2);
        assertTrue(repository.update(account));
        account.setAccount_id(50);
        assertFalse(repository.update(account));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    private Account makeAccount() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(443.23));
        account.setBank_id(1);
        account.setCustomer_id(1);
        return account;
    }
}