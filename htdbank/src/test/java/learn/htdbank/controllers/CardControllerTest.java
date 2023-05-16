package learn.htdbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.CardRepository;
import learn.htdbank.models.Card;
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
class CardControllerTest {
    @MockBean
    CardRepository repository;
    @Autowired
    MockMvc mvc;
    @Test
    void shouldFindAll() throws Exception{
        Card actual = new Card();
        actual.setCard_id(1);
        actual.setType("Credit");
        actual.setAccount_id(1);
        actual.setCustomer_id(1);

        Card expected = new Card();
        expected.setCard_id(2);
        expected.setType("Credit");
        expected.setAccount_id(1);
        expected.setCustomer_id(1);

        List<Card> cards = List.of(actual, expected);
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(cards);
        when(repository.findAll()).thenReturn(cards);

        mvc.perform(get("/api/card"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Card actual = new Card();
        actual.setCard_id(0);
        actual.setType("Credit");
        actual.setAccount_id(1);
        actual.setCustomer_id(1);

        Card expected = new Card();
        expected.setCard_id(1);
        expected.setType("Credit");
        expected.setAccount_id(1);
        expected.setCustomer_id(1);

        when(repository.add(actual)).thenReturn(expected);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(actual);

        var request = post("/api/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFindById() throws Exception {
        int cardId = 1;
        Card actual = new Card();
        actual.setCard_id(1);
        actual.setType("Credit");
        actual.setAccount_id(1);
        actual.setCustomer_id(1);

        when(repository.findById(cardId)).thenReturn(actual);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(actual);

        mvc.perform(get("/api/card/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdate() throws Exception {
        Card actual = new Card();
        actual.setCard_id(1);
        actual.setType("Credit");
        actual.setAccount_id(1);
        actual.setCustomer_id(1);

        when(repository.update(actual)).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = put("/api/card/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        int bankId = 1;
        when(repository.deleteById(bankId)).thenReturn(true);
        mvc.perform(delete("/api/card/1"))
                .andExpect(status().isOk());
    }
}