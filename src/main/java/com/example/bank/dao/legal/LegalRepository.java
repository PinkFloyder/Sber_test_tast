package com.example.bank.dao.legal;

import com.example.bank.entity.person.legal.LegalPerson;

import javax.persistence.NoResultException;
import java.util.List;

public interface LegalRepository {

    public LegalPerson addNewLegalPerson(String address, String type, String title) throws NoResultException;

    public List<LegalPerson> getListLegalPerson();

    public Float incrementBalanceFromLegalScore(String score_number, Float sumIncrement);

}
