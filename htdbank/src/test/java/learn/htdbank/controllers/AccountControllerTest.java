package learn.htdbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.AccountRepository;
import learn.htdbank.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class AccountControllerTest {
    @MockBean
    AccountRepository repository;
    @Autowired
    MockMvc mvc;
    @Test
    void shouldFindAll() throws Exception {
        Account actual = new Account();
        actual.setAccount_id(1);
        actual.setCustomer_id(1);
        actual.setBank_id(1);
        actual.setBalance(BigDecimal.valueOf(34.34));

        Account expected = new Account();
        expected.setAccount_id(2);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(34.34));

        List<Account> accounts = List.of(actual, expected);
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(accounts);
        when(repository.findAll()).thenReturn(accounts);

        mvc.perform(get("/api/account"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Account actual = new Account();
        actual.setAccount_id(0);
        actual.setCustomer_id(1);
        actual.setBank_id(1);
        actual.setBalance(BigDecimal.valueOf(34.34));

        Account expected = new Account();
        expected.setAccount_id(1);
        expected.setCustomer_id(1);
        expected.setBank_id(1);
        expected.setBalance(BigDecimal.valueOf(34.34));

        when(repository.add(actual)).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(actual);

        var request = post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFindById() throws Exception {
        int actualId = 1;
        Account account = new Account();
        account.setAccount_id(1);
        account.setBalance(BigDecimal.valueOf(45.45));

        when(repository.findById(actualId)).thenReturn(account);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(account);

        mvc.perform(get("/api/account/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }


    @Test
    void shouldUpdate() throws Exception {
        Account actual = new Account();
        actual.setAccount_id(1);
        actual.setCustomer_id(1);
        actual.setBank_id(1);
        actual.setBalance(BigDecimal.valueOf(34.34));

        when(repository.update(actual)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = put("/api/account/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteById() throws Exception{
        int accountId = 1;
        when(repository.deleteById(accountId)).thenReturn(true);
        mvc.perform(delete("/api/account/1"))
                .andExpect(status().isOk());
    }
}