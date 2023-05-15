package learn.htdbank.data;

import learn.htdbank.models.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EmployeeTemplateRepository implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    public EmployeeTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        final String sql = "SELECT employee_id, first_name, last_name, salary, bank_id FROM Employee;";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmployee_id(resultSet.getInt("employee_id"));
            employee.setFirst_name(resultSet.getString("first_name"));
            employee.setLast_name(resultSet.getString("last_name"));
            employee.setSalary(resultSet.getBigDecimal("salary"));
            employee.setBank_id(resultSet.getInt("bank_id"));
            return employee;
        });
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        final String sql = "SELECT employee_id, first_name, last_name, salary, bank_id FROM Employee WHERE employee_id = ?";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmployee_id(resultSet.getInt("employee_id"));
            employee.setFirst_name(resultSet.getString("first_name"));
            employee.setLast_name(resultSet.getString("last_name"));
            employee.setSalary(resultSet.getBigDecimal("salary"));
            employee.setBank_id(resultSet.getInt("bank_id"));
            return employee;
        }, id).stream().findFirst().orElse(null);
    }

    @Override
    public Employee add(Employee employee) {
        KeyHolder key = new GeneratedKeyHolder();
        final String sql = "INSERT INTO Employee (first_name, last_name, salary, bank_id " +
                "VALUES (?, ?, ?, ?);";

        int rowsInserted = jdbcTemplate.update((connect) -> {
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirst_name());
            ps.setString(2, employee.getLast_name());
            ps.setBigDecimal(3, employee.getSalary());
            ps.setInt(4, employee.getBank_id());
            return ps;
        }, key);

        //if add failed
        if(rowsInserted <= 0) {
            return null;
        }
        employee.setEmployee_id(key.getKey().intValue());
        return employee;
    }

    @Override
    @Transactional
    public boolean update(Employee employee) {
        final String sql = "UPDATE Employee SET first_name=?, last_name=?, salary=?, bank_id=? WHERE employee_id = ?;";
        int rowsUpdated = jdbcTemplate.update(sql, employee.getFirst_name(), employee.getLast_name(), employee.getSalary(), employee.getBank_id(), employee.getEmployee_id());
        return rowsUpdated > 0;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        final String sql = "DELETE FROM Employee WHERE employee_id = ?;";
        int rowsDeleted = jdbcTemplate.update(sql, id);
        return rowsDeleted > 0;
    }
}
