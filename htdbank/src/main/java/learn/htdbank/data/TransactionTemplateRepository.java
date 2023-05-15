package learn.htdbank.data;

import learn.htdbank.data.mappers.TransactionMapper;
import learn.htdbank.models.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionTemplateRepository implements TransactionRepository {

    private JdbcTemplate jdbcTemplate;

    public TransactionTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> findAll() {
        final String sql = "SELECT transaction_id, transaction_type, amount FROM Transaction;";
        return jdbcTemplate.query(sql, new TransactionMapper());
    }

    @Override
    @Transactional
    public Transaction findById(int id) {
        final String sql = "SELECT transaction_id, transaction_type, amount FROM Transaction WHERE transaction_id = ?;";
        return jdbcTemplate.query(sql, new TransactionMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public Transaction add(Transaction trans) {
        KeyHolder key = new GeneratedKeyHolder();
        final String sql = "INSERT INTO Transaction (transaction_type, amount) VALUES (?, ?);";
        int rowsInserted = jdbcTemplate.update((connect) -> {
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, trans.getTransaction_type());
            ps.setBigDecimal(2, trans.getAmount());
            return ps;
        }, key);

        if(rowsInserted <= 0) {
            return null;
        }

        trans.setTransaction_id(key.getKey().intValue());
        return trans;
    }

    @Override
    @Transactional
    public boolean update(Transaction trans) {
        final String sql = "UPDATE Transaction SET transaction_type=?, amount=? WHERE transaction_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sql, trans.getTransaction_type(), trans.getAmount(), trans.getTransaction_id());
        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        final String sql = "DELETE FROM Transaction WHERE transaction_id = ?;";
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }
}
