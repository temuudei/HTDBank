package learn.htdbank.data;

import learn.htdbank.data.mappers.AccountMapper;
import learn.htdbank.models.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class AccountJdbcTemplateRepository implements AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        final String sql = "select account_id, customer_id, bank_id, account_balance "
                + "from `Account` limit 1000;";
        return jdbcTemplate.query(sql, new AccountMapper());
    }

    @Override
    @Transactional
    public Account findById(int account_id) {
        final String sql = "select account_id, customer_id, bank_id, account_balance "
                + "from `Account` where account_id = ?;";

    }

    @Override
    @Transactional
    public Account add(Account account) {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Account account) {
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(int account_id) {
        return false;
    }

    private void addCards(Account account) {
        final String sql = "select a.account_id, "
    }
}
