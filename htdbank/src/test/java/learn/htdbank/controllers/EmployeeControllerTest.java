package learn.htdbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.htdbank.data.EmployeeRepository;
import learn.htdbank.models.Customer;
import learn.htdbank.models.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @MockBean
    EmployeeRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindById() throws Exception {
        int employee_id = 1;
        Employee employee = new Employee();
        employee.setFirst_name("John");
        employee.setLast_name("Whick");
        employee.setSalary(BigDecimal.valueOf(348.23));
        employee.setBank_id(1);
        when(repository.findById(employee_id)).thenReturn(employee);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(employee);

        mvc.perform(get("/api/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Employee input = makeValidEmployee();
        Employee output = makeValidEmployee();
        output.setEmployee_id(3);

        when(repository.add(input)).thenReturn(output);
        ObjectMapper jsonMapper = new ObjectMapper();
        String inJson = jsonMapper.writeValueAsString(input);

        var request = post("/api/employee").contentType(MediaType.APPLICATION_JSON).content(inJson);
        mvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdate() throws Exception {
        Employee employee = new Employee();
        employee.setEmployee_id(1);
        employee.setFirst_name("John");
        employee.setLast_name("Whick");
        employee.setSalary(BigDecimal.valueOf(1000));

        when(repository.update(any())).thenReturn(true);
        ObjectMapper jsonMapper = new ObjectMapper();
        String output = jsonMapper.writeValueAsString(employee);

        var request = put("/api/employee/1").contentType(MediaType.APPLICATION_JSON).content(output);
        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void shouldDelete() throws Exception {
        int employee_id = 1;
        when(repository.delete(employee_id)).thenReturn(true);
        mvc.perform(delete("/api/employee/1"))
                .andExpect(status().isNoContent());
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
