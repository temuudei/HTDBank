package learn.htdbank.data.mappers;

import learn.htdbank.models.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {


    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction trans = new Transaction();
        trans.setTransaction_id(resultSet.getInt("transaction_id"));
        trans.setTransaction_type(resultSet.getString("transaction_type"));
        trans.setAmount(resultSet.getBigDecimal("amount"));
        return trans;
    }
}
