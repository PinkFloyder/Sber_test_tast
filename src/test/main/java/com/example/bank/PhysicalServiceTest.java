package com.example.bank;

import com.example.bank.dao.physical.PhysicalRepository;
import com.example.bank.service.physical.PhysicalService;
import com.example.bank.utils.CardForList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.NoResultException;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PhysicalServiceTest {

    @Autowired
    private PhysicalService service;

    @MockBean
    private PhysicalRepository repository;

    @Test
    public void createNewCartCrashTest() {
        String score_number = "score_number";
        String message = "Данного счета не существует";
        when(repository.createNewCart(score_number))
                .thenThrow(new NoResultException(message));

        ResponseEntity<String> expected = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        ResponseEntity<String> actual = service.createNewCart(score_number);
        assertEquals(expected, actual);
    }

    @Test
    public void createNewCartTest() {
        String score_number = "1020304050506060707070605";
        String card_number = "1111222233334445";
        when(repository.createNewCart(score_number))
                .thenReturn(card_number);
        ResponseEntity<String> expected = new ResponseEntity<>(card_number, HttpStatus.CREATED);
        ResponseEntity<String> actual = service.createNewCart(score_number);
        assertEquals(expected, actual);
    }

    @Test
    public void getListNumberCardTest() {
        String card_number = "4276160498345534";
        List<CardForList> expectedValue = Collections.singletonList(
                new CardForList(1, card_number));
        ResponseEntity<List<CardForList>> expected = new ResponseEntity<>(expectedValue, HttpStatus.OK);

        List<String> responseRepository = Collections.singletonList(card_number);
        when(repository.getListNumberCard())
                .thenReturn(responseRepository);
        ResponseEntity<List<CardForList>> actual = service.getListNumberCard();

        assertEquals(expected, actual);
    }

    @Test
    public void incrementBalanceFromCardNumberCrashTest() {
        String card_number = "card_number";
        String message = "Карты с данным номером не существует";

        ResponseEntity<String> expected = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        when(repository.incrementBalanceFromCardNumber(card_number, 100f))
                .thenThrow(new NoResultException(message));
        ResponseEntity<String> actual = service.incrementBalanceFromCardNumber(card_number, 100f);

        assertEquals(expected, actual);
    }

    @Test
    public void incrementBalanceFromCardNumberTest() {
        String card_number = "4276160498345534";
        float expectedBalance = 200.88f;
        ResponseEntity<String> expected = new ResponseEntity<>(String.valueOf(expectedBalance), HttpStatus.ACCEPTED);

        when(repository.incrementBalanceFromCardNumber(card_number, 100f))
                .thenReturn(expectedBalance);
        ResponseEntity<String> actual = service.incrementBalanceFromCardNumber(card_number, 100f);

        assertEquals(expected, actual);
    }

    @Test
    public void getBalanceFromCardNumberCrashTest() {
        String card_number = "card_number";
        String message = "Карты с таким номером нет";

        ResponseEntity<String> expected = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        when(repository.getBalanceFromCardNumber(card_number))
                .thenThrow(new NoResultException(message));
        ResponseEntity<String> actual = service.getBalanceFromCardNumber(card_number);

        assertEquals(expected, actual);
    }

    @Test
    public void getBalanceFromCardNumberTest() {
        String card_number = "4276160498345534";
        float value = 300;

        ResponseEntity<String> expected = new ResponseEntity<>(String.valueOf(value), HttpStatus.OK);

        when(repository.getBalanceFromCardNumber(card_number))
                .thenReturn(value);
        ResponseEntity<String> actual = service.getBalanceFromCardNumber(card_number);

        assertEquals(expected, actual);
    }
}