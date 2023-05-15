package learn.htdbank.data.mappers;

import learn.htdbank.models.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<Employee> {


    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployee_id(resultSet.getInt("employee_id"));
        employee.setFirst_name(resultSet.getString("first_name"));
        employee.setLast_name(resultSet.getString("last_name"));
        employee.setSalary(resultSet.getBigDecimal("salary"));
        employee.setBank_id(resultSet.getInt("bank_id"));
        return employee;
    }
}
