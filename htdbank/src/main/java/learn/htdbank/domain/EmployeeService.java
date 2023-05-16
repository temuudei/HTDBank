package learn.htdbank.domain;

import learn.htdbank.data.EmployeeRepository;
import learn.htdbank.models.Employee;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee findById(int id) {
        return repository.findById(id);
    }

    public Result<Employee> add(Employee employee) {
        Result<Employee> result = validate(employee);

        if(!result.isSuccess()) {
            return result;
        }

        //check that an id is not already assigned
        if(employee.getEmployee_id() != 0) {
            result.addErrorMessage("An ID must not already be set before add.");
            return result;
        }

        //okay to add
        employee = repository.add(employee);
        result.setPayload(employee);
        return result;
    }

    public Result<Employee> update(Employee employee) {
        Result<Employee> result = validate(employee);

        if(!result.isSuccess()) {
            return result;
        }

        //verify that it has valid id
        if(employee.getEmployee_id() <= 0) {
            result.addErrorMessage("Employee must have an ID set before update.");
            return result;
        }

        //attempt to update
        if(!repository.update(employee)) {
            result.addErrorMessage("Employee not found.");
            return result;
        }

        return result;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    private Result<Employee> validate(Employee employee) {
        Result<Employee> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //check against annotation validations
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        //store error messages (if any)
        if(violations.size() > 0) {
            for(ConstraintViolation<Employee> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        return result;
    }
}
