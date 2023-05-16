package learn.htdbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.BankRepository;
import learn.htdbank.models.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest {
    @MockBean
    BankRepository repository;
    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception{
        Bank actual = new Bank();
        actual.setBank_id(1);
        actual.setRouting_number(999999999);

        Bank expected = new Bank();
        expected.setBank_id(2);
        expected.setRouting_number(999999999);

        List<Bank> banks = List.of(actual, expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(banks);
        when(repository.findAll()).thenReturn(banks);

        mvc.perform(get("/api/bank"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Bank actual = new Bank();
        actual.setBank_id(0);
        actual.setRouting_number(999999999);

        Bank expected = new Bank();
        expected.setBank_id(1);
        expected.setRouting_number(999999999);

        when(repository.add(actual)).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(actual);

        var request = post("/api/bank")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFindById() throws Exception {
        int bankId = 1;
        Bank actual = new Bank();
        actual.setBank_id(1);
        actual.setRouting_number(999999999);

        when(repository.findById(bankId)).thenReturn(actual);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(actual);

        mvc.perform(get("/api/bank/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdate() throws Exception {
        Bank actual = new Bank();
        actual.setBank_id(1);
        actual.setRouting_number(999999999);

        when(repository.update(actual)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = put("/api/bank/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteById() throws Exception {
        int bankId = 1;
        when(repository.deleteById(bankId)).thenReturn(true);
        mvc.perform(delete("/api/bank/1"))
                .andExpect(status().isOk());
    }
}