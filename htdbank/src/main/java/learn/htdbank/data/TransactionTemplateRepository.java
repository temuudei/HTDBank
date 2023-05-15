package learn.htdbank.data;

import learn.htdbank.models.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionTemplateRepository implements TransactionRepository {

    private JdbcTemplate jdbcTemplate;

    public TransactionTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> findAll() {
        final String sql = "SELECT transaction_id, action, amount FROM Transaction;";
    }

    @Override
    @Transactional
    public Transaction findById(int id) {
        return null;
    }

    @Override
    public Transaction add(Transaction trans) {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Transaction trans) {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return false;
    }
}
