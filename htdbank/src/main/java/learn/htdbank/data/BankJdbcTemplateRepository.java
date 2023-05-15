package learn.htdbank.data;

import learn.htdbank.data.mappers.AccountMapper;
import learn.htdbank.data.mappers.BankMapper;
import learn.htdbank.data.mappers.EmployeeMapper;
import learn.htdbank.models.Bank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
    public Bank add(Bank bank) {
        final String sql = "insert into Bank (routing_number) values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bank.getRouting_number());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        bank.setBank_id(keyHolder.getKey().intValue());
        return bank;
    }

    @Override
    @Transactional
    public boolean update(Bank bank) {
        final String sql = "update Bank set "
                + "routing_number = ? "
                + "where bank_id = ?;";
        return jdbcTemplate.update(sql,
                bank.getRouting_number(),
                bank.getBank_id()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int bank_id) {
        jdbcTemplate.update("delete c from Card c inner join `Account` a on c.account_id = a.account_id where a.bank_id = ?;", bank_id);
        jdbcTemplate.update("delete from `Account` where bank_id = ?;", bank_id);
        jdbcTemplate.update("delete from Employee where bank_id = ?;", bank_id);
        return jdbcTemplate.update("delete from Bank where bank_id = ?;", bank_id) > 0;
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
        final String sql = "select e.employee_id, e.first_name, e.last_name, e.salary, e.bank_id, "
                + "b.routing_number "
                + "from Employee e "
                + "inner join Bank b on e.bank_id = b.bank_id "
                + "where e.bank_id = ?;";
        var bankEmployees = jdbcTemplate.query(sql, new EmployeeMapper(), bank.getBank_id());
        bank.setEmployees(bankEmployees);
    }
}
