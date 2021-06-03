package com.example.bank.service.legal;

import com.example.bank.dao.legal.LegalRepository;
import com.example.bank.entity.person.legal.LegalAccount;
import com.example.bank.entity.person.legal.LegalPerson;
import com.example.bank.utils.PersonForList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LegalServiceImpl implements LegalService {

    @Autowired
    private LegalRepository legalRepository;

    @Override
    public ResponseEntity<String> addNewLegalPerson(String address, String type, String title) {
        try {
            LegalPerson newPerson = legalRepository.addNewLegalPerson(address, type, title);
            String score_number = newPerson.getAccounts().get(0).getScore_number();
            return new ResponseEntity<>(score_number, HttpStatus.CREATED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<List<PersonForList>> getListLegalPerson() {
        List<LegalPerson> personList = legalRepository.getListLegalPerson();
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
            String newBalance = String.valueOf(legalRepository.incrementBalanceFromLegalScore(score_number, sumIncrement));
            return new ResponseEntity<>(newBalance, HttpStatus.ACCEPTED);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
