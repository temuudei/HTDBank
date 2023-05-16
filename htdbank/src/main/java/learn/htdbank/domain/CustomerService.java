package learn.htdbank.domain;

import learn.htdbank.data.CustomerRepository;
import learn.htdbank.models.Customer;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService {
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(int id) {
        return repository.findById(id);
    }

    public Result<Customer> add(Customer customer) {
        Result<Customer> result = validate(customer);
        if(!result.isSuccess()) {
            return result;
        }

        //verify that id is not already set
        if(customer.getCustomer_id() != 0) {
            result.addErrorMessage("Customer ID must not already be set before add operation.");
            return result;
        }


        //okay to add
        customer = repository.add(customer);
        result.setPayload(customer);
        return result;
    }

    public Result<Customer> update(Customer customer) {
        Result<Customer> result = validate(customer);

        if(!result.isSuccess()) {
            return result;
        }

        //verify that customer has an ID
        if(customer.getCustomer_id() <= 0) {
            result.addErrorMessage("Customer must have an ID before update operation.");
            return result;
        }

        //Do not allow duplicate SSN
        if(repository.findAll().stream().anyMatch(i -> i.getSsn() == customer.getSsn())) {
            result.addErrorMessage("SSN cannot be duplicated.");
            return result;
        }

        //attempt to update
        if(!repository.update(customer)) {
            result.addErrorMessage("Customer not found.");
        }
        return result;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    private Result<Customer> validate(Customer customer) {
        Result<Customer> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //check against Validation annotations in model
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if(!violations.isEmpty()) {

            //add error message(s)
           for(ConstraintViolation<Customer> violation : violations) {
               result.addErrorMessage(violation.getMessage());
           }
           return result;
        }

        return result;
    }
}
