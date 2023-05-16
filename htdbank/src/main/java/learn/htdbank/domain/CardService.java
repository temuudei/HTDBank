package learn.htdbank.domain;

import learn.htdbank.data.CardRepository;
import learn.htdbank.models.Card;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public Card findById(int card_id) {
        return repository.findById(card_id);
    }

    public Result<Card> add(Card card) {
        Result<Card> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Card> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (card.getCard_id() != 0) {
            result.addErrorMessage("Card ID cannot be set for `add` operation");
            return result;
        }

        card = repository.add(card);
        result.setPayload(card);
        return result;
    }

    public Result<Card> update(Card card) {
        Result<Card> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Card>> violations = validator.validate(card);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Card> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (card.getCard_id() <= 0) {
            result.addErrorMessage("Card ID must be set for `update` operation");
            return result;
        }

        if (!repository.update(card)) {
            String msg = String.format("Card ID: %s, not found", card.getCard_id());
            result.addErrorMessage(msg);
        }

        return result;
    }

    public boolean deleteById(int card_id) {
        return repository.deleteById(card_id);
    }
}
