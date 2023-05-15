package learn.htdbank.data;

import learn.htdbank.models.Card;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardRepository {
    @Transactional
    List<Card> findAll();
    @Transactional
    Card findById(int card_id);
    @Transactional
    Card add(Card card);
    @Transactional
    boolean update(Card card);
    @Transactional
    boolean deleteById(int card_id);
}
