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
        Account accountOne = new Account();
        accountOne.setAccount_id(1);
        accountOne.setBalance(BigDecimal.valueOf(454.34));
        accountOne.setBank_id(1);
        accountOne.setCustomer_id(1);

        System.out.println(repository.findById(1));
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}