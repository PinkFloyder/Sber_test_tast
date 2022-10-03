package com.example.bank.controller;

import com.example.bank.service.legal.LegalService;
import com.example.bank.service.physical.PhysicalService;
import com.example.bank.utils.PersonForList;
import com.example.bank.utils.CardForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankRestController {

    @Autowired
    PhysicalService physicalService;

    @Autowired
    LegalService legalService;

    @GetMapping("/balance/{card_number}")
    public ResponseEntity<String> getBalanceFromCardNumber(@PathVariable String card_number) {
        return physicalService.getBalanceFromCardNumber(card_number);
    }

    @GetMapping("/allCard" )
    public ResponseEntity<List<CardForList>> getListNumberCard() {
        return physicalService.getListNumberCard();
    }

    @PutMapping("/createCard/{score_number}")
    public ResponseEntity<String> creatNewCard(@PathVariable String score_number) {
        return physicalService.createNewCart(score_number);
    }

    @PostMapping("/increaseCard")
    public ResponseEntity<String> incrementBalanceFromCardNumber(@RequestParam String card_number,
                                                                 @RequestParam Float sumIncrement) {
        return physicalService.incrementBalanceFromCardNumber(card_number, sumIncrement);
    }

//    <--------------------> 2 ЭТАП <-------------------->

    @PutMapping("/addLegalPerson")
    public ResponseEntity<String> addNewLegalPerson(@RequestParam("address") String address,
                                                    @RequestParam("type") String type,
                                                    @RequestParam("title") String title) {
        return legalService.addNewLegalPerson(address, type, title);
    }

    @GetMapping("/allLegal")
    public ResponseEntity<List<PersonForList>> getListLegalPerson() {
        return legalService.getListLegalPerson();
    }

    @PostMapping("/increaseLegal")
    public ResponseEntity<String> incrementBalanceFromLegalScore(@RequestParam("score_number") String score_number,
                                                                 @RequestParam("sumIncrement") Float sumIncrement) {
        return legalService.incrementBalanceFromLegalScore(score_number, sumIncrement);
    }
}
