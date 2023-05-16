package learn.htdbank.controllers;

import learn.htdbank.domain.Result;
import learn.htdbank.domain.TransactionService;
import learn.htdbank.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.font.TransformAttribute;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> readAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Transaction readById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    ResponseEntity<Object> add(@RequestBody Transaction trans) {
        Result<Transaction> result = service.add(trans);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> update(@PathVariable int id, @RequestBody Transaction trans) {
        //verify that IDs match
        if(id != trans.getTransaction_id()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Result<Transaction> result = service.update(trans);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable int id) {
        if(service.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


