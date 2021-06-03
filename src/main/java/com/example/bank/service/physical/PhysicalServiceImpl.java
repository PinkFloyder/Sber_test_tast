package com.example.bank.service.physical;

import com.example.bank.dao.physical.PhysicalRepository;
import com.example.bank.utils.CardForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalServiceImpl implements PhysicalService {

    @Autowired
    private PhysicalRepository physicalRepository;

    @Override
    public ResponseEntity<String> createNewCart(String score_number) {
        try {
            return new ResponseEntity<>(physicalRepository.createNewCart(score_number), HttpStatus.CREATED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<CardForList>> getListNumberCard() {
        List<String> stringList = physicalRepository.getListNumberCard();
        List<CardForList> resultList = new ArrayList<>(stringList.size());
        for (int i = 0; i < stringList.size(); i++) {
            resultList.add(new CardForList(i + 1, stringList.get(i)));
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> incrementBalanceFromCardNumber(String card_number, Float sumIncrement) {
        try {
            String newCardNumber = String.valueOf(physicalRepository.incrementBalanceFromCardNumber(card_number, sumIncrement));
            return new ResponseEntity<>(newCardNumber, HttpStatus.ACCEPTED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> getBalanceFromCardNumber(String card_number) {
        try {
            String balance = String.valueOf(physicalRepository.getBalanceFromCardNumber(card_number));
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }
}
