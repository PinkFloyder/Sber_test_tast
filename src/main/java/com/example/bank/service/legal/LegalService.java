package com.example.bank.service.legal;

import com.example.bank.utils.PersonForList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LegalService {

    public ResponseEntity<String> addNewLegalPerson(String address, String type, String title);

    public ResponseEntity<List<PersonForList>> getListLegalPerson();

    public ResponseEntity<String> incrementBalanceFromLegalScore(String score_number, Float sumIncrement);

}
