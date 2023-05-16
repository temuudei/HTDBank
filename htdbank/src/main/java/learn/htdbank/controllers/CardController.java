package learn.htdbank.controllers;

import learn.htdbank.domain.CardService;
import learn.htdbank.models.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/card")
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping
    public List<Card> findAll() {
        return service.findAll();
    }
    @GetMapping("/{card_id}")
    public Card findById(@PathVariable int card_id) {
        return service.findById(card_id);
    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Card card, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.add(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{card_id}")
    public ResponseEntity<Object> update(@PathVariable int card_id, @RequestBody @Valid Card card, BindingResult result) {
        if (card_id != card.getCard_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.update(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{card_id}")
    public ResponseEntity<Void> deleteById(@PathVariable int card_id) {
        if (service.deleteById(card_id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
