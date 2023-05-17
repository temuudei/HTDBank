package learn.htdbank.data;

import learn.htdbank.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionTemplateRepositoryTest {

    @Autowired
    TransactionTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        //Arrange
        List<Transaction> trans = new ArrayList<>();

        //Act
        trans = repository.findAll();

        //Assert
        assertTrue(trans.size() > 0);
    }

    @Test
    void shouldFindById() {
        //Act
        Transaction trans = repository.findById(2);

        //Assert
        assertNotNull(trans);
        assertEquals(BigDecimal.valueOf(65.46), trans.getAmount());
    }

    @Test
    void shouldAdd() {
        //Arrange
        Transaction expected = makeValidTransaction();

        //Act
        Transaction actual = repository.add(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(actual.getTransaction_id(), 3);
    }

    @Test
    void shouldUpdate() {
        //Arrange
        Transaction trans = new Transaction();
        trans.setAmount(new BigDecimal(100.00));
        trans.setTransaction_type("Deposit");
        trans.setTransaction_id(2);

        //Act Assert
        assertTrue(repository.update(trans));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.delete(2));
    }

    private Transaction makeValidTransaction() {
        Transaction trans = new Transaction();
        trans.setTransaction_type("Deposit");
        trans.setAmount(new BigDecimal(100));
        return trans;
    }
}