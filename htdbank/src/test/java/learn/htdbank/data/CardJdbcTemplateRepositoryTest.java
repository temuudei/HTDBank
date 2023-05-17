package learn.htdbank.data;

import learn.htdbank.models.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CardJdbcTemplateRepositoryTest {
    @Autowired
    CardJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;
    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

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
        card.setAccount_id(1);
        card.setCustomer_id(1);

        Card actual = repository.findById(1);
        assertEquals(card.getCard_id(), actual.getCard_id());
        assertEquals(card.getType(), actual.getType());
    }

    @Test
    void shouldAdd() {
        Card card = makeCard();
        Card actual = repository.add(card);
        assertNotNull(actual);
        assertEquals(3, actual.getCard_id());
    }

    @Test
    void shouldUpdate() {
        Card card = makeCard();
        card.setCard_id(2);
        assertTrue(repository.update(card));
        card.setCard_id(50);
        assertFalse(repository.update(card));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    private Card makeCard() {
        Card card = new Card();
        card.setType("Credit");
        card.setAccount_id(1);
        card.setCustomer_id(1);
        return card;
    }
}