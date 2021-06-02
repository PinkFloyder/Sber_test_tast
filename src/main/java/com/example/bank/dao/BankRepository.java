package com.example.bank.dao;

import com.example.bank.entity.legal_person.LegalPerson;

import javax.persistence.NoResultException;
import java.util.List;

public interface BankRepository {

    public String createNewCart(String score_number) throws NoResultException;

    public List<String> getListNumberCard();

    public Float incrementBalanceFromCardNumber(String card_number, Float sumIncrement) throws NoResultException;

    public Float getBalanceFromCardNumber(String number) throws NoResultException;

//  <--------------------> 2 ЭТАП <-------------------->

    public LegalPerson addNewLegalPerson(String address, String type, String title) throws NoResultException;

    public List<LegalPerson> getListLegalPerson();

    public Float incrementBalanceFromLegalScore(String score_number, Float sumIncrement);

}
