package learn.htdbank.domain;

import learn.htdbank.data.TransactionTemplateRepository;
import learn.htdbank.models.Employee;
import learn.htdbank.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService service;

    @MockBean
    TransactionTemplateRepository repository;

    @Test
    void shouldAdd() {
        //Arrange
        Transaction expected = makeValidTransaction();

        //Act
        Transaction output = makeValidTransaction();
        output.setTransaction_id(3);
        when(repository.add(expected)).thenReturn(output);
        Result<Transaction> actual = service.add(expected);

        //Assert
        assertEquals(output, actual.getPayload());

    }

    @Test
    void shouldNotAddInvalid() {
        Transaction trans = makeValidTransaction();
        trans.setTransaction_id(3);
        Result<Transaction> result = service.add(trans);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalid() {
        Transaction trans = makeValidTransaction();
        trans.setTransaction_type("withdrawal");
        Result<Transaction> result = service.update(trans);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdate() {
        //Arrange
        Transaction expected = makeValidTransaction();
        expected.setTransaction_id(3);
        expected.setTransaction_type("withdrawal");

        //Act
        when(repository.update(expected)).thenReturn(true);
        Result<Transaction> actual = service.update(expected);

        //Assert
        assertTrue(actual.isSuccess());
    }



    private Transaction makeValidTransaction() {
        Transaction trans = new Transaction();
        trans.setTransaction_type("Deposit");
        trans.setAmount(new BigDecimal(100));
        return trans;
    }
}
