package learn.htdbank.data;

import learn.htdbank.models.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> findAll();

    @Transactional
    Employee findById(int id);

    Employee add(Employee employee);

    @Transactional
    boolean update(Employee employee);

    @Transactional
    boolean delete(int id);
}
