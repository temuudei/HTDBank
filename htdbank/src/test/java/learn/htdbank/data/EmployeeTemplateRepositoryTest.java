package learn.htdbank.data;

import learn.htdbank.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeTemplateRepositoryTest {
    @Autowired
    EmployeeTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        //Arrange
        List<Employee> employees = new ArrayList<>();

        //Act
        employees = repository.findAll();

        //Assert
        assertTrue(employees.size() > 0);
    }

    @Test
    void shouldFindById() {
        //Act
        Employee actual = repository.findById(2);

        //Assert
        assertNotNull(actual);
        assertEquals("Lebron", actual.getFirst_name());

    }

    @Test
    void shouldAdd() {
        //Arrange
        Employee expected = makeValidEmployee();

        //Act
        Employee actual = repository.add(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(3, actual.getEmployee_id());
    }

    @Test
    void shouldUpdate() {
        //Arrange
        Employee actual = new Employee();
        actual.setFirst_name("Steph");
        actual.setLast_name("Curry");
        actual.setBank_id(2);
        actual.setSalary(new BigDecimal(9001.00));
        actual.setEmployee_id(2);

        //Assert
        assertTrue(repository.update(actual));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.delete(2));
    }

    private Employee makeValidEmployee() {
        Employee employee = new Employee();
        employee.setFirst_name("John");
        employee.setLast_name("Cena");
        employee.setBank_id(2);
        employee.setSalary(new BigDecimal(8000.00));

        return employee;
    }
}