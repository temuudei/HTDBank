package learn.htdbank.controllers;

import learn.htdbank.domain.AccountService;
import learn.htdbank.models.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> findAll() {
        return service.findAll();
    }
    @GetMapping("/{account_id}")
    public Account findById(@PathVariable int account_id) {
        return service.findById(account_id);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Account account, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.add(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{account_id}")
    public ResponseEntity<Object> update(@PathVariable int account_id, @RequestBody @Valid Account account, BindingResult result) {
        if (account_id != account.getAccount_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.update(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{account_id}")
    public ResponseEntity<Void> deleteById(@PathVariable int account_id) {
        if (service.deleteById(account_id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
