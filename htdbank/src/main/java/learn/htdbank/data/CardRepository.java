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
    Card add(Card account);
    @Transactional
    boolean update(Card account);
    @Transactional
    boolean deleteById(int card_id);
}
