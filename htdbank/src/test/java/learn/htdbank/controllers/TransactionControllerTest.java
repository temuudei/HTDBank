package learn.htdbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.TransactionRepository;
import learn.htdbank.models.Customer;
import learn.htdbank.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @MockBean
    TransactionRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindById() throws Exception {
        int transaction_id = 1;
        Transaction transaction = new Transaction();
        transaction.setTransaction_type("Deposit");
        transaction.setAmount(BigDecimal.valueOf(23231.56));
        when(repository.findById(transaction_id)).thenReturn(transaction);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(transaction);

        mvc.perform(get("/api/transaction/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Transaction input = makeValidTransaction();
        Transaction actual = makeValidTransaction();
        actual.setTransaction_id(3);

        when(repository.add(input)).thenReturn(actual);
        ObjectMapper jsonMapper = new ObjectMapper();
        String inJson = jsonMapper.writeValueAsString(input);

        var request = post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(inJson);
        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdate() throws Exception {
        Transaction expected = new Transaction();
        expected.setTransaction_id(1);
        expected.setTransaction_type("Deposit");
        expected.setAmount(BigDecimal.valueOf(90001));

        when(repository.update(any())).thenReturn(true);
        ObjectMapper jsonMapper = new ObjectMapper();
        String output = jsonMapper.writeValueAsString(expected);

        var request = put("/api/transaction/1").contentType(MediaType.APPLICATION_JSON).content(output);
        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteById() throws Exception {
        int transaction_id = 1;
        when(repository.delete(transaction_id)).thenReturn(true);
        mvc.perform(delete("/api/customer/1"))
                .andExpect(status().isNoContent());
    }

    private Transaction makeValidTransaction() {
        Transaction trans = new Transaction();
        trans.setTransaction_type("Deposit");
        trans.setAmount(new BigDecimal(100));
        return trans;
    }
}
