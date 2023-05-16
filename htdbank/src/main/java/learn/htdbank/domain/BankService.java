package learn.htdbank.domain;

import learn.htdbank.data.BankRepository;
import learn.htdbank.models.Bank;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class BankService {
    private final BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    public List<Bank> findAll() {
        return repository.findAll();
    }

    public Bank findById(int bank_id) {
        return repository.findById(bank_id);
    }

    public Result<Bank> add(Bank bank) {
        Result<Bank> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Bank>> violations = validator.validate(bank);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Bank> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (bank.getBank_id() != 0) {
            result.addErrorMessage("Bank ID cannot be set for `add` operation");
            return result;
        }

        bank = repository.add(bank);
        result.setPayload(bank);
        return result;
    }

    public Result<Bank> update(Bank bank) {
        Result<Bank> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Bank>> violations = validator.validate(bank);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Bank> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (bank.getBank_id() <= 0) {
            result.addErrorMessage("Bank ID must be set for `update` operation");
            return result;
        }

        if (!repository.update(bank)) {
            String msg = String.format("Bank ID: %s, not found", bank.getBank_id());
            result.addErrorMessage(msg);
        }

        return result;
    }

    public boolean deleteById(int bank_id) {
        return repository.deleteById(bank_id);
    }
}
