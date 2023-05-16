package learn.htdbank.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.CustomerRepository;
import learn.htdbank.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @MockBean
    CustomerRepository repository;
    
    @Autowired
    MockMvc mvc;
    
    @Test
    void shouldAdd() throws Exception {
        Customer input = makeValidCustomer();
        Customer actual = makeValidCustomer();
        actual.setCustomer_id(3);

        when(repository.add(input)).thenReturn(actual);
        ObjectMapper jsonMapper = new ObjectMapper();
        String inJson = jsonMapper.writeValueAsString(input);

        var request = post("/api/customer").contentType(MediaType.APPLICATION_JSON).content(inJson);
        mvc.perform(request)
                .andExpect(status().isCreated());

    }

    @Test
    void shouldUpdate() throws Exception {
        Customer expected = new Customer();
        expected.setCustomer_id(2);
        expected.setFirst_name("Giblert");
        expected.setLast_name("Keys");
        expected.setSsn(987654321);

        when(repository.update(any())).thenReturn(true);
        ObjectMapper jsonMapper = new ObjectMapper();
        String output = jsonMapper.writeValueAsString(expected);

        var request = put("/api/customer/2").contentType(MediaType.APPLICATION_JSON).content(output);
        mvc.perform(request)
                .andExpect(status().isOk());

    }

    @Test
    void shouldFindById() throws Exception {
        int customer_id = 1;
        Customer customer = new Customer();
        customer.setFirst_name("Giblert");
        customer.setLast_name("Keys");
        customer.setSsn(987654321);
        when(repository.findById(customer_id)).thenReturn(customer);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(customer);

        mvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldDeleteById() throws Exception {
        int customer_id = 1;
        when(repository.delete(customer_id)).thenReturn(true);
        mvc.perform(delete("/api/customer/1"))
                .andExpect(status().isNoContent());
    }
    private Customer makeValidCustomer() {
        Customer customer = new Customer();
        customer.setFirst_name("John");
        customer.setLast_name("Doe");
        customer.setSsn(5555555);

        return customer;
    }
}
