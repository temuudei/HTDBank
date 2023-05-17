package learn.htdbank.domain;

import learn.htdbank.data.CustomerTemplateRepository;
import learn.htdbank.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @MockBean
    CustomerTemplateRepository repository;

    @Test
    void shouldAdd() {
        //Arrange
        Customer expected = makeValidCustomer();
        expected.setCustomer_id(0);

        Customer output = makeValidCustomer();
        output.setCustomer_id(3);

        //Act
        when(repository.add(expected)).thenReturn(output);
        Result<Customer> actual = service.add(expected);

        //assert
        assertEquals(output, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalid() {
        Customer customer = makeValidCustomer();
        customer.setCustomer_id(3);
        Result<Customer> result = service.add(customer);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldUpdate() {
        //Arrange
        Customer expected = makeValidCustomer();
        expected.setCustomer_id(3);
        expected.setLast_name("Appleseed");

        //Act
        when(repository.update(expected)).thenReturn(true);
        Result<Customer> actual = service.update(expected);

        //Assert
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalid() {

        Customer customer = makeValidCustomer();
        Result<Customer> result = service.update(customer);
        assertFalse(result.isSuccess());
    }


    private Customer makeValidCustomer() {
        Customer customer = new Customer();
        customer.setFirst_name("John");
        customer.setLast_name("Doe");
        customer.setSsn(5555555);

        return customer;
    }
}
