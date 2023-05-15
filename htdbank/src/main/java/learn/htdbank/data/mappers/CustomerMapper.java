package learn.htdbank.data.mappers;

import learn.htdbank.models.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomer_id(resultSet.getInt("customer_id"));
        customer.setFirst_name(resultSet.getString("first_name"));
        customer.setLast_name(resultSet.getString("last_name"));
        customer.setSsn(resultSet.getInt("ssn"));
        return customer;
    }
}
