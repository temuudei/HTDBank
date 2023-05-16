package learn.htdbank.domain;

import learn.htdbank.data.TransactionRepository;
import learn.htdbank.models.Transaction;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {

    TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction findById(int id) {
        return repository.findById(id);
    }

    public Result<Transaction> add(Transaction trans) {
        Result<Transaction> result = validate(trans);
        if(!result.isSuccess()) {
            return result;
        }

        //must not have id before adding
        if(trans.getTransaction_id() != 0) {
            result.addErrorMessage("An id must not already be set before add.");
            return result;
        }

        //okay to add
        trans = repository.add(trans);
        result.setPayload(trans);
        return result;
    }

    public Result<Transaction> update(Transaction trans) {
        Result<Transaction> result = validate(trans);
        if(!result.isSuccess()) {
            return result;
        }

        //check that transaction has an id
        if(trans.getTransaction_id() <= 0) {
            result.addErrorMessage("Transaction must have a valid id for update.");
            return result;
        }

        //attempt to update
        if(!repository.update(trans)) {
            result.addErrorMessage("Transaction not found.");
            return result;
        }

        return result;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    private Result<Transaction> validate(Transaction trans) {
        Result<Transaction> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(trans);

        //store error messages (if any)
        if(violations.size() > 0) {
            for(ConstraintViolation<Transaction> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        return result;
    }
}
