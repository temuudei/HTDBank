package learn.htdbank.data;

import learn.htdbank.models.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CustomerTemplateRepository implements CustomerRepository {

    private JdbcTemplate jdbcTemplate;

    public CustomerTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
       final String sql = "SELECT customer_id, first_name, last_name, ssn FROM Customer;";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Customer customer = new Customer();
            customer.setCustomer_id(resultSet.getInt("customer_id"));
            customer.setFirst_name(resultSet.getString("first_name"));
            customer.setLast_name(resultSet.getString("last_name"));
            customer.setSsn(resultSet.getInt("ssn"));
            return customer;
        });
    }

    @Override
    @Transactional
    public Customer findById(int id) {
       final String sql = "SELECT customer_id, first_name, last_name, ssn FROM Customer WHERE customer_id = ?;";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Customer customer = new Customer();
            customer.setCustomer_id(resultSet.getInt("customer_id"));
            customer.setFirst_name(resultSet.getString("first_name"));
            customer.setLast_name(resultSet.getString("last_name"));
            customer.setSsn(resultSet.getInt("ssn"));
            return customer;
        }, id).stream().findFirst().orElse(null);
    }

    @Override
    public Customer add(Customer customer) {
        KeyHolder key = new GeneratedKeyHolder();
        final String sql = "INSERT INTO Customer (first_name, last_name, ssn) " +
                "VALUES (?, ?, ?);";
        int rowsInserted = jdbcTemplate.update((connect) -> {
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirst_name());
            ps.setString(2, customer.getLast_name());
            ps.setInt(3, customer.getSsn());
            return ps;
        }, key );

        //if add fails
        if(rowsInserted <= 0) {
            return null;
        }
        customer.setCustomer_id(key.getKey().intValue());
        return customer;
    }

    @Override
    @Transactional
    public boolean update(Customer customer) {
        final String sql = "UPDATE Customer SET first_name=?, last_name=?, ssn=? WHERE customer_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sql, customer.getFirst_name(), customer.getLast_name(), customer.getSsn(), customer.getCustomer_id());
        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        final String sql = "DELETE FROM Customer WHERE customer_id = ?;";
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }
}
