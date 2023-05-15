package learn.htdbank.data;

import learn.htdbank.data.mappers.AccountMapper;
import learn.htdbank.data.mappers.BankMapper;
import learn.htdbank.models.Bank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BankJdbcTemplateRepository implements BankRepository{
    private final JdbcTemplate jdbcTemplate;

    public BankJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<Bank> findAll() {
        final String sql = "select bank_id, routing_number from Bank limit 1000;";
        return jdbcTemplate.query(sql, new BankMapper());
    }

    @Override
    @Transactional
    public Bank findById(int bank_id) {
        final String sql = "select bank_id, routing_number from Bank where bank_id = ?;";
        Bank result = jdbcTemplate.query(sql, new BankMapper(), bank_id)
                .stream()
                .findFirst().orElse(null);
        if (result != null) {
            addEmployees(result);
            addAccounts(result);
        }
        return result;
    }

    @Override
    @Transactional
    public Bank add(Bank account) {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Bank account) {
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(int bank_id) {
        return false;
    }

    private void addAccounts(Bank bank) {
        final String sql = "select a.account_id, a.customer_id, a.bank_id, a.account_balance, "
                + "b.routing_number "
                + "from Account a "
                + "inner join Bank b on a.bank_id = b.bank_id "
                + "where a.bank_id = ?;";
        var bankAccounts = jdbcTemplate.query(sql, new AccountMapper(), bank.getBank_id());
        bank.setAccounts(bankAccounts);
    }

    private void addEmployees(Bank bank) {
    }
}
