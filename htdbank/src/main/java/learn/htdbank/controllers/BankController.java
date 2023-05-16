package learn.htdbank.controllers;

import learn.htdbank.domain.BankService;
import learn.htdbank.models.Bank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/bank")
public class BankController {
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bank> findAll() {
        return service.findAll();
    }

    @GetMapping("/{bank_id}")
    public Bank findById(@PathVariable int bank_id) {
        return service.findById(bank_id);

    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Bank bank, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.add(bank);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{bank_id}")
    public ResponseEntity<Object> update(@PathVariable int bank_id, @RequestBody @Valid Bank bank, BindingResult result) {
        if (bank_id != bank.getBank_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.update(bank);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bank_id}")
    public ResponseEntity<Void> deleteById(@PathVariable int bank_id) {
        if (service.deleteById(bank_id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
