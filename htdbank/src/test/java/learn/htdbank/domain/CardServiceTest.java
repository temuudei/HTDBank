package learn.htdbank.domain;

import learn.htdbank.data.CardRepository;
import learn.htdbank.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class CardServiceTest {
    @Autowired
    CardService service;
    @MockBean
    CardRepository repository;
    @Test
    void shouldFindAll() {
        List<Card> cards = repository.findAll();
        assertNotNull(cards);
    }

    @Test
    void shouldFindById() {
        Card card = new Card();
        card.setCard_id(1);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        when(repository.findById(1)).thenReturn(card);
        Card actual = repository.findById(1);
        assertEquals(card, actual);
    }

    @Test
    void shouldAdd() {
        Card card = new Card();
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        Result<Card> result = service.add(card);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldUpdate() {
        Card card = new Card();
        card.setCard_id(1);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        when(repository.update(card)).thenReturn(true);
        Result<Card> result = service.update(card);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void deleteById() {
        Card card = new Card();
        card.setCard_id(1);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        when(repository.deleteById(card.getCard_id())).thenReturn(true);
    }

    @Test
    void shouldFailValidationWhenCardIsNull() {
        Card card = new Card();
        Result<Card> result = service.add(card);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNotZero() {
        Card card = new Card();
        card.setCard_id(1);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        Result<Card> result = service.add(card);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNegativeForUpdate() {
        Card card = new Card();
        card.setCard_id(-1);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        Result<Card> result = service.update(card);
        assertNotEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldFailValidationWhenIdIsNotFoundForUpdate() {
        Card card = new Card();
        card.setCard_id(8);
        card.setType("Credit");
        card.setCustomer_id(1);
        card.setAccount_id(1);

        Result<Card> result = service.update(card);
        assertNotEquals(0, result.getErrorMessages().size());
    }
}