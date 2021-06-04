package com.example.bank;

import com.example.bank.dao.legal.LegalRepositoryImpl;
import com.example.bank.dao.physical.PhysicalRepositoryImpl;
import com.example.bank.entity.person.legal.LegalAccount;
import com.example.bank.entity.person.legal.LegalPerson;
import com.example.bank.entity.person.physical.Card;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class BankRepositoryImplTest {

    @Autowired
    private PhysicalRepositoryImpl physicalRepository;

    @Autowired
    private LegalRepositoryImpl legalRepository;

    @Test
    public void initMaxCardNumberEmptyTest() {
        BigInteger expected = new BigInteger("1111222233334444");
        ReflectionTestUtils.invokeMethod(physicalRepository, "initMaxCardNumber");
        BigInteger actual = (BigInteger) ReflectionTestUtils.getField(physicalRepository, "max_card_number");
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void initMaxCardNumberTest() {
        BigInteger expected = new BigInteger("4276160498345534");
        ReflectionTestUtils.invokeMethod(physicalRepository, "initMaxCardNumber");
        BigInteger actual = (BigInteger) ReflectionTestUtils.getField(physicalRepository, "max_card_number");
        assertEquals(expected, actual);
    }

    @Test
    public void initMaxLegalScoreNumberEmptyTest() {
        BigInteger expected = new BigInteger("11111111112222222222");
        ReflectionTestUtils.invokeMethod(legalRepository, "initMaxLegalScoreNumber");
        BigInteger actual = (BigInteger) ReflectionTestUtils.getField(legalRepository, "max_legal_score_number");
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void initMaxLegalScoreNumberTest() {
        BigInteger expected = new BigInteger("983467346237646");
        ReflectionTestUtils.invokeMethod(legalRepository, "initMaxLegalScoreNumber");
        BigInteger actual = (BigInteger) ReflectionTestUtils.getField(legalRepository, "max_legal_score_number");
        assertEquals(expected, actual);
    }

    @Test
    public void getNewDateTest() {
        Date expected = new GregorianCalendar(2022, Calendar.JANUARY , 25).getTime();
        GregorianCalendar calendar = new GregorianCalendar(2018, Calendar.JANUARY , 25);
        Date testData = calendar.getTime();
        Date actual = ReflectionTestUtils.invokeMethod(physicalRepository, "getNewDate", testData);
        assertEquals(expected, actual);
    }

    @Test
    public void findNumberScoreEmptyTest() {
        String score_number = "1020304050506060707070605";
        Boolean actual = physicalRepository.findNumberScore(score_number);
        assertFalse(actual);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findNumberScoreTest() {
        String score_number = "1020304050506060707070605";
        Boolean actual = physicalRepository.findNumberScore(score_number);
        assertTrue(actual);
    }

    @Test(expected = NoResultException.class)
    public void createNewCartCrashTest() {
        String score_number = "score_number";
        physicalRepository.createNewCart(score_number);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewCartTest() {
        String score_number = "1020304050506060707070605";
        String expected_card_number = "1111222233334445";
        String actual_card_number = physicalRepository.createNewCart(score_number);
        EntityManager entityManager = (EntityManager) ReflectionTestUtils.getField(physicalRepository, "entityManager");
        Session session = entityManager.unwrap(Session.class);
        List<Card> actualScore = session
                .createQuery("from Card where score_number = :score_number", Card.class)
                .setParameter("score_number", score_number)
                .getResultList();
        session.close();
        Integer expectedQuantityCard = 2;
        Integer actualQuantityCard = actualScore.size();
        boolean checkContains = actualScore.stream().anyMatch(x -> x.getCard_number().equals(expected_card_number));

        assertEquals(expected_card_number, actual_card_number);
        assertEquals(expectedQuantityCard, actualQuantityCard);
        assertTrue(checkContains);
    }

    @Test
    public void getListNumberCardCEmptyTest() {
        List<String> expected = Collections.emptyList();
        List<String> actual = physicalRepository.getListNumberCard();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getListNumberCardCTest() {
        List<String> expected = Collections.singletonList("4276160498345534");
        List<String> actual = physicalRepository.getListNumberCard();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void incrementBalanceFromCardNumberTest() {
        String card_number = "4276160498345534";
        Float sumIncrement = 100f;
        Float expectedValue = 200.88f;
        Float actualReturn = physicalRepository.incrementBalanceFromCardNumber(card_number, sumIncrement);
        EntityManager entityManager = (EntityManager) ReflectionTestUtils.getField(physicalRepository, "entityManager");
        Session session = entityManager.unwrap(Session.class);
        Float actualValueInDataBase = session
                .createQuery("select balance from Card where card_number = :card_number", Float.class)
                .setParameter("card_number", card_number)
                .getSingleResult();
        session.close();
        assertEquals(expectedValue, actualReturn);
        assertEquals(expectedValue, actualValueInDataBase);
    }

    @Test(expected = NoResultException.class)
    public void incrementBalanceFromCardNumberCrashTest() {
        String card_number = "testNumberCard";
        physicalRepository.incrementBalanceFromCardNumber(card_number, 0f);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getBalanceFromCardNumberTest() {
        String card_number = "4276160498345534";
        Float expected = 100.88f;
        Float actual = physicalRepository.getBalanceFromCardNumber(card_number);
        assertEquals(expected, actual);
    }

    @Test(expected = NoResultException.class)
    public void getBalanceFromCardNumberCrashTest() {
        String card_number = "card_number";
        physicalRepository.getBalanceFromCardNumber(card_number);
    }

//    <--------------------> 2 ЭТАП <-------------------->

    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test(expected = NoResultException.class)
    public void addNewLegalPersonCrashTest() {
        String address = "56A49274940F5744";
        String type = "AAA";
        String title = "Tinkoff";
        legalRepository.addNewLegalPerson(address, type, title);
    }

    @Test
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewLegalPersonTest() {
        String address = "56A49274940F5744";
        String type = "AAA";
        String title = "Tinkoff";
        legalRepository.addNewLegalPerson(address, type, title);
        EntityManager entityManager = (EntityManager) ReflectionTestUtils.getField(physicalRepository, "entityManager");
        Session session = entityManager.unwrap(Session.class);
        LegalPerson actualLegalPerson = session
                .createQuery("from LegalPerson where address = :address", LegalPerson.class)
                .setParameter("address", address)
                .getSingleResult();
        session.close();
        assertEquals(address, actualLegalPerson.getAddress());
        assertEquals(type, actualLegalPerson.getType());
        assertEquals(title, actualLegalPerson.getTitle());
    }

    @Test
    public void getListLegalPersonEmptyTest() {
        List<LegalPerson> actualList = legalRepository.getListLegalPerson();
        assertEquals(Collections.emptyList(), actualList);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getListLegalPersonTest() {
        List<LegalPerson> expectedList =  Arrays.asList(
                new LegalPerson("9389E5736D205746",  "OOO", "Sberbank",
                        Collections.singletonList(new LegalAccount( "018486147512746", "9389E5736D205746",322.22f))),
                new LegalPerson("56A49274940F5744", "AAA", "Tinkoff"
                        , Collections.singletonList(new LegalAccount( "763849348138478", "56A49274940F5744", 1488.88f))),
                new LegalPerson("3483938W474947C5", "OAO", "Yandex",
                        Collections.singletonList(new LegalAccount( "983467346237646", "3483938W474947C5",1337.77f)))
        );
        List<LegalPerson> actualList = legalRepository.getListLegalPerson();
        actualList.sort(Comparator.comparing(LegalPerson::getTitle));
        assertEquals(expectedList, actualList);
    }

    @Test(expected = NoResultException.class)
    public void incrementBalanceFromLegalScoreCrashTest() {
        String score_number = "score_number";
        legalRepository.incrementBalanceFromLegalScore(score_number, 0f);
    }

    @Test
    @Sql(value = {"create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void incrementBalanceFromLegalScoreTest() {
        String score_number = "018486147512746";
        Float sumIncrement = 100f;
        Float expectedValue = 422.22f;
        Float actualReturn = legalRepository.incrementBalanceFromLegalScore(score_number, sumIncrement);
        EntityManager entityManager = (EntityManager) ReflectionTestUtils.getField(physicalRepository, "entityManager");
        Session session = entityManager.unwrap(Session.class);
        Float actualValueInDataBase = session
                .createQuery("select balance from LegalAccount where score_number = :score_number", Float.class)
                .setParameter("score_number", score_number)
                .getSingleResult();
        session.close();
        assertEquals(expectedValue, actualReturn);
        assertEquals(expectedValue, actualValueInDataBase);
    }
}