package com.example.bank.service;

import com.example.bank.supporting.PersonForList;
import com.example.bank.supporting.CardForList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankService {

    public ResponseEntity<String> createNewCart(String score_number);

    public ResponseEntity<List<CardForList>> getListNumberCard();

    public ResponseEntity<String> incrementBalanceFromCardNumber(String card_number, Float sumIncrement);

    public ResponseEntity<String> getBalanceFromCardNumber(String card_number);

//    <--------------------> 2 ЭТАП <-------------------->

    public ResponseEntity<String> addNewLegalPerson(String address, String type, String title);

    public ResponseEntity<List<PersonForList>> getListLegalPerson();

    public ResponseEntity<String> incrementBalanceFromLegalScore(String score_number, Float sumIncrement);
}
