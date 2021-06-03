package com.example.bank.dao.physical;


import javax.persistence.NoResultException;
import java.util.List;

public interface PhysicalRepository {

    public String createNewCart(String score_number) throws NoResultException;

    public List<String> getListNumberCard();

    public Float incrementBalanceFromCardNumber(String card_number, Float sumIncrement) throws NoResultException;

    public Float getBalanceFromCardNumber(String number) throws NoResultException;

}
