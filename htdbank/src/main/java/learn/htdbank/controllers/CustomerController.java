package learn.htdbank.controllers;

import learn.htdbank.domain.CustomerService;
import learn.htdbank.domain.Result;
import learn.htdbank.models.Bank;
import learn.htdbank.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/customer")
public class CustomerController {

    CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> readAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer readById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Customer customer) {
        Result<Customer> result = service.add(customer);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Customer customer, @PathVariable int id) {
        //verify that IDs match
        if(id != customer.getCustomer_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Customer> result = service.update(customer);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if(service.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
