package learn.htdbank.domain;

import learn.htdbank.data.AccountRepository;
import learn.htdbank.models.Account;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(int account_id) {
        return repository.findById(account_id);
    }

    public Result<Account> add(Account account) {
        Result<Account> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Account> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (account.getAccount_id() != 0 ) {
            result.addErrorMessage("Account ID cannot be set for `add` operation");
            return result;
        }

        account = repository.add(account);
        result.setPayload(account);
        return result;
    }

    public Result<Account> update(Account account) {
        Result<Account> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Account> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        if (account.getAccount_id() <= 0) {
            result.addErrorMessage("Account ID must be set for `update` operation");
            return result;
        }

        if (!repository.update(account)) {
            String msg = String.format("Account ID: %s, not found", account.getAccount_id());
            result.addErrorMessage(msg);
        }

        return result;
    }

    public boolean deleteById(int account_id) {
        return repository.deleteById(account_id);
    }
}
