package learn.htdbank.domain;

import learn.htdbank.data.EmployeeTemplateRepository;
import learn.htdbank.models.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService service;

    @MockBean
    EmployeeTemplateRepository repository;

    @Test
    void shouldAdd() {
        //Arrange
        Employee expected = makeValidEmployee();

        //Act
        Employee output = makeValidEmployee();
        output.setEmployee_id(3);
        when(repository.add(expected)).thenReturn(output);
        Result<Employee> actual = service.add(expected);

        //Assert
        assertEquals(output, actual.getPayload());
    }

    @Test
    void shouldNotAddInvalid() {
        Employee employee = makeValidEmployee();
        employee.setEmployee_id(3);
        Result<Employee> result = service.add(employee);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldNotUpdateInvalid() {
        Employee employee = makeValidEmployee();
        Result<Employee> result = service.update(employee);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdate() {
        //Arrange
        Employee expected = makeValidEmployee();
        expected.setEmployee_id(3);
        expected.setSalary(BigDecimal.valueOf(9001));

        //Act
        when(repository.update(expected)).thenReturn(true);
        Result<Employee> actual = service.update(expected);

        //Assert
        assertTrue(actual.isSuccess());
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
