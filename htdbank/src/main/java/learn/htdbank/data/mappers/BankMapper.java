package learn.htdbank.data.mappers;

import learn.htdbank.models.Bank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankMapper implements RowMapper<Bank> {

    @Override
    public Bank mapRow(ResultSet resultSet, int i) throws SQLException {
        Bank bank = new Bank();
        bank.setBank_id(resultSet.getInt("bank_id"));
        bank.setRouting_number(resultSet.getInt("routing_number"));
        return bank;
    }
}
