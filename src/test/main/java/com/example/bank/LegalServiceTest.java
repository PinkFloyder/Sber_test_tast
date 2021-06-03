package com.example.bank;

import com.example.bank.dao.legal.LegalRepository;
import com.example.bank.entity.person.legal.LegalAccount;
import com.example.bank.entity.person.legal.LegalPerson;
import com.example.bank.service.legal.LegalService;
import com.example.bank.utils.PersonForList;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class LegalServiceTest {

    @Autowired
    private LegalService service;

    @MockBean
    private LegalRepository repository;

    @Test
    public void addNewLegalPersonTest() {
        String score_number = "11111111112222222223";
        String address = "56A49274940F5744";
        String type = "AAA";
        String title = "Tinkoff";

        LegalAccount legalAccount = new LegalAccount(score_number, address, 0);
        LegalPerson newPerson = new LegalPerson(address, type, title, Collections.singletonList(legalAccount));
        when(repository.addNewLegalPerson(address, type, title))
                .thenReturn(newPerson);

        ResponseEntity<String> expected = new ResponseEntity<>(score_number, HttpStatus.CREATED);
        ResponseEntity<String> actual = service.addNewLegalPerson(address, type, title);

        assertEquals(expected, actual);
    }

    @Test
    public void addNewLegalPersonCrashTest() {
        String message = "Данное юридисеское лицо уже зарагестрировано";
        String address = "56A49274940F5744";
        String type = "AAA";
        String title = "Tinkoff";

        when(repository.addNewLegalPerson(address, type, title))
                .thenThrow(new NoResultException(message));

        ResponseEntity<String> expected = new ResponseEntity<>(message, HttpStatus.CONFLICT);
        ResponseEntity<String> actual = service.addNewLegalPerson(address, type, title);

        assertEquals(expected, actual);
    }

    @Test
    public void getListLegalPersonTest() {
        String score_number = "763849348138478";
        String address = "56A49274940F5744";
        String type = "AAA";
        String title = "Tinkoff";

        List<PersonForList> expectedValue = Collections.singletonList(
                new PersonForList(title,Collections.singletonList(score_number)));

        ResponseEntity<List<PersonForList>> expected = new ResponseEntity<>(expectedValue, HttpStatus.ACCEPTED);

        List<LegalPerson> responseRepository = Collections.singletonList(
                new LegalPerson(address, type, title, Collections.singletonList(new LegalAccount(score_number, address, 0))));

        when(repository.getListLegalPerson())
                .thenReturn(responseRepository);
        ResponseEntity<List<PersonForList>> actual = service.getListLegalPerson();

        assertEquals(expected, actual);
    }

    @Test
    public void incrementBalanceFromLegalScoreTest() {
        String score_number = "763849348138478";
        float expectedBalance = 200.88f;
        ResponseEntity<String> expected = new ResponseEntity<>(String.valueOf(expectedBalance), HttpStatus.ACCEPTED);

        when(repository.incrementBalanceFromLegalScore(score_number, 100f))
                .thenReturn(expectedBalance);
        ResponseEntity<String> actual = service.incrementBalanceFromLegalScore(score_number, 100f);

        assertEquals(expected, actual);
    }

    @Test
    public void incrementBalanceFromLegalScoreCrashTest() {
        String score_number = "score_number";
        String message = "Юридического лица с данным номером не существует";

        ResponseEntity<String> expected = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        when(repository.incrementBalanceFromLegalScore(score_number, 100f))
                .thenThrow(new NoResultException(message));
        ResponseEntity<String> actual = service.incrementBalanceFromLegalScore(score_number, 100f);

        assertEquals(expected, actual);
    }
}
