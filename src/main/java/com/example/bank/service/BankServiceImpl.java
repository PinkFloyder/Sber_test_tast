package com.example.bank.service;

import com.example.bank.supporting.PersonForList;
import com.example.bank.dao.BankRepositoryImpl;
import com.example.bank.entity.legal_person.LegalAccount;
import com.example.bank.entity.legal_person.LegalPerson;
import com.example.bank.supporting.CardForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private BankRepositoryImpl bankRepository;

    @Override
    public ResponseEntity<String> createNewCart(String score_number) {
        try {
            return new ResponseEntity<>(bankRepository.createNewCart(score_number), HttpStatus.CREATED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<CardForList>> getListNumberCard() {
        List<String> stringList = bankRepository.getListNumberCard();
        List<CardForList> resultList = new ArrayList<>(stringList.size());
        for (int i = 0; i < stringList.size(); i++) {
            resultList.add(new CardForList(i + 1, stringList.get(i)));
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> incrementBalanceFromCardNumber(String card_number, Float sumIncrement) {
        try {
            String newCardNumber = String.valueOf(bankRepository.incrementBalanceFromCardNumber(card_number, sumIncrement));
            return new ResponseEntity<>(newCardNumber, HttpStatus.ACCEPTED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> getBalanceFromCardNumber(String card_number) {
        try {
            String balance = String.valueOf(bankRepository.getBalanceFromCardNumber(card_number));
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
        }

    }

//    <--------------------> 2 ЭТАП <-------------------->

    @Override
    public ResponseEntity<String> addNewLegalPerson(String address, String type, String title) {
        try {
            LegalPerson newPerson = bankRepository.addNewLegalPerson(address, type, title);
            String score_number = newPerson.getAccounts().get(0).getScore_number();
            return new ResponseEntity<>(score_number, HttpStatus.CREATED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<List<PersonForList>> getListLegalPerson() {
        List<LegalPerson> personList = bankRepository.getListLegalPerson();
        List<PersonForList> resultList = new ArrayList<>(personList.size());
        for (LegalPerson legalPerson : personList) {
            List<String> score_number_list = legalPerson
                    .getAccounts()
                    .stream()
                    .map(LegalAccount::getScore_number)
                    .collect(Collectors.toList());
            resultList.add(new PersonForList(legalPerson.getTitle(), score_number_list));
        }
        return new ResponseEntity<>(resultList, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> incrementBalanceFromLegalScore(String score_number, Float sumIncrement) {
        try {
            String newBalance = String.valueOf(bankRepository.incrementBalanceFromLegalScore(score_number, sumIncrement));
            return new ResponseEntity<>(newBalance, HttpStatus.ACCEPTED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
