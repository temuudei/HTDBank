package learn.htdbank.data;

import learn.htdbank.data.mappers.AccountMapper;
import learn.htdbank.data.mappers.CardMapper;
import learn.htdbank.models.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        Account result = jdbcTemplate.query(sql, new AccountMapper(), account_id)
                .stream()
                .findFirst().orElse(null);
        if (result != null) {
            addCards(result);
        }
        return result;
    }

    @Override
    @Transactional
    public Account add(Account account) {
        final String sql = "insert into `Account` (customer_id, bank_id, account_balance) values (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, account.getCustomer_id());
            ps.setInt(2, account.getBank_id());
            ps.setBigDecimal(3, account.getBalance());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        account.setAccount_id(keyHolder.getKey().intValue());
        return account;
    }

    @Override
    @Transactional
    public boolean update(Account account) {
        final String sql = "update `Account` set "
                + "customer_id = ?, "
                + "bank_id = ?, "
                + "account_balance = ? "
                + "where account_id = ?;";
        return jdbcTemplate.update(sql,
                account.getCustomer_id(),
                account.getBank_id(),
                account.getBalance(),
                account.getAccount_id()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int account_id) {
        jdbcTemplate.update("delete from Card where account_id = ?;", account_id);
        return jdbcTemplate.update("delete from `Account` where account_id = ?;", account_id) > 0;
    }

    private void addCards(Account account) {
        final String sql = "select a.account_id, a.customer_id, a.bank_id, a.account_balance, "
                + "c.card_id, c.`type` types "
                + "from Card c "
                + "inner join `Account` a on c.account_id = a.account_id "
                + "where c.account_id = ?;";
        var accountCards = jdbcTemplate.query(sql, new CardMapper(), account.getAccount_id());
        account.setCards(accountCards);
    }
}
