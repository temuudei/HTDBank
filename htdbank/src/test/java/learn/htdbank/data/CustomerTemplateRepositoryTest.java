package learn.htdbank.data;

import learn.htdbank.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerTemplateRepositoryTest {

    @Autowired
    CustomerTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        //Arrange
        List<Customer> customers = new ArrayList<>();

        //Act
        customers = repository.findAll();

        //Assert
        assertTrue(customers.size() > 0);
    }

    @Test
    void shouldFindById() {
        //Arrange act
        Customer customer = repository.findById(2);

        //Assert
        assertNotNull(customer);
        assertEquals("Temuudei", customer.getFirst_name());
    }

    @Test
    void shouldAdd() {
        //Arrange
        Customer expected = makeValidCustomer();

        //Act
        Customer actual = repository.add(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(3, actual.getCustomer_id());
    }

    @Test
    void shouldUpdate() {
        //Arrange
        Customer actual = new Customer();
        actual.setFirst_name("Temuudei");
        actual.setSsn(123456789);
        actual.setCustomer_id(2);
        actual.setLast_name("Cena");

        //Act assert
        assertTrue(repository.update(actual));
    }

    @Test
    void shouldDelete() {

        assertTrue(repository.delete(2));
    }
    private Customer makeValidCustomer() {
        Customer customer = new Customer();
        customer.setFirst_name("John");
        customer.setLast_name("Doe");
        customer.setSsn(5555555);

        return customer;
    }
}