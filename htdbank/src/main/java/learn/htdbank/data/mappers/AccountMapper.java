package learn.htdbank.data.mappers;

import learn.htdbank.models.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setAccount_id(resultSet.getInt("account_id"));
        account.setBalance(resultSet.getBigDecimal("account_balance"));

    }
}
