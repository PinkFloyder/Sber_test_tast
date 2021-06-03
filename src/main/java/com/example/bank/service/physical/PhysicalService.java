package com.example.bank.service.physical;

import com.example.bank.utils.CardForList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PhysicalService {

    public ResponseEntity<String> createNewCart(String score_number);

    public ResponseEntity<List<CardForList>> getListNumberCard();

    public ResponseEntity<String> incrementBalanceFromCardNumber(String card_number, Float sumIncrement);

    public ResponseEntity<String> getBalanceFromCardNumber(String card_number);

}
