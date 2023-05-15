package learn.htdbank.data;

import learn.htdbank.data.mappers.AccountMapper;
import learn.htdbank.data.mappers.CardMapper;
import learn.htdbank.data.mappers.CustomerMapper;
import learn.htdbank.models.Card;
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
        return jdbcTemplate.query(sql, new CustomerMapper());
    }

    @Override
    @Transactional
    public Customer findById(int id) {
       final String sql = "SELECT customer_id, first_name, last_name, ssn FROM Customer WHERE customer_id = ?;";
        Customer cu = jdbcTemplate.query(sql, new CustomerMapper(), id).stream().findFirst().orElse(null);

        if(cu != null) {
            addCards(cu);
            addAccounts(cu);
        }
        return cu;
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

        //delete from Card and Account first due to foreign keys
        jdbcTemplate.update("DELETE FROM Card WHERE customer_id = ?;", id);
        jdbcTemplate.update("DELETE FROM Account WHERE customer_id = ?;", id);

        //delete from Customer
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }

    //a customer has one or more cards
    private void addCards(Customer customer) {
        final String sql = "SELECT ca.card_id, ca.type, ca.account_id, ca.customer_id, cu.first_name, cu.last_name FROM Card ca " +
                "INNER JOIN Customer cu ON ca.customer_id = cu.customer_id WHERE cu.customer_id = ?;";

        var cards = jdbcTemplate.query(sql, new CardMapper(), customer.getCustomer_id());
        customer.setCards(cards);
    }

    //a customer has one or more accounts
    private void addAccounts(Customer customer) {
        final String sql = "SELECT a.account_id, a.customer_id, a.bank_id, a.account_balance, c.first_name, c.last_name FROM Account a " +
                "INNER JOIN Customer c ON a.customer_id = c.customer_id WHERE c.customer_id = ?;";
        var accounts = jdbcTemplate.query(sql, new AccountMapper(), customer.getCustomer_id());
        customer.setAccounts(accounts);
    }
}
