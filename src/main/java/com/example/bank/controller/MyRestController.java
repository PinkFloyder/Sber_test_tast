package com.example.bank.controller;

import com.example.bank.service.BankServiceImpl;
import com.example.bank.supporting.PersonForList;
import com.example.bank.supporting.CardForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class MyRestController {

    @Autowired
    BankServiceImpl bankService;

    @GetMapping("/balance/{card_number}")
    public ResponseEntity<String> getBalanceFromCardNumber(@PathVariable String card_number) {
        return bankService.getBalanceFromCardNumber(card_number);
    }

    @GetMapping("/allCard" )
    public ResponseEntity<List<CardForList>> getListNumberCard() {
        return bankService.getListNumberCard();
    }

    @PutMapping("/createCard/{score_number}")
    public ResponseEntity<String> creatNewCard(@PathVariable String score_number) {
        return bankService.createNewCart(score_number);
    }

    @PostMapping("/increaseCard")
    public ResponseEntity<String> incrementBalanceFromCardNumber(@RequestParam String card_number,
                                                                 @RequestParam Float sumIncrement) {
        return bankService.incrementBalanceFromCardNumber(card_number, sumIncrement);
    }

//    <--------------------> 2 ЭТАП <-------------------->

    @PutMapping("/addLegalPerson")
    public ResponseEntity<String> addNewLegalPerson(@RequestParam("address") String address,
                                                    @RequestParam("type") String type,
                                                    @RequestParam("title") String title) {
        return bankService.addNewLegalPerson(address, type, title);
    }

    @GetMapping("/allLegal")
    public ResponseEntity<List<PersonForList>> getListLegalPerson() {
        return bankService.getListLegalPerson();
    }

    @PostMapping("/increaseLegal")
    public ResponseEntity<String> incrementBalanceFromLegalScore(@RequestParam("score_number") String score_number,
                                                                 @RequestParam("sumIncrement") Float sumIncrement) {
        return bankService.incrementBalanceFromLegalScore(score_number, sumIncrement);
    }
}
