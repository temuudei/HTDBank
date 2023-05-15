package learn.htdbank.data;

import learn.htdbank.models.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository {
    @Transactional
    List<Account> findAll();
    @Transactional
    Account findById(int account_id);
    @Transactional
    Account add(Account account);
    @Transactional
    boolean update(Account account);
    @Transactional
    boolean deleteById(int account_id);
}
