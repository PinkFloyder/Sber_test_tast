package com.example.bank.dao.physical;

import com.example.bank.entity.person.physical.Card;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Repository
public class PhysicalRepositoryImpl implements PhysicalRepository {

    @Autowired
    private EntityManager entityManager;

    public static BigInteger max_card_number;


    @PostConstruct
    public void initMaxCardNumber() {
        try (Session session = entityManager.unwrap(Session.class)) {
            String current_max_card_number = session.createQuery("select max(card_number) from Card", String.class).getSingleResult();
            max_card_number = new BigInteger(current_max_card_number);
        } catch (NullPointerException e) {
            max_card_number = new BigInteger("1111222233334444");
        }
    }


    private Date getNewDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 4);
        return cal.getTime();
    }


    public Boolean findNumberScore(String score_number) {
        try (Session session = entityManager.unwrap(Session.class)) {
            session.createQuery(" select score_number from Score where score_number = :score_number", String.class)
                    .setParameter("score_number", score_number)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }


    @Override
    @Transactional
    public String createNewCart(String score_number) throws NoResultException {
        if (!findNumberScore(score_number)) {
            throw new NoResultException("Данного счета не существует");
        }
        max_card_number = max_card_number.add(new BigInteger("1"));
        Card card = new Card(max_card_number.toString(), getNewDate(new Date()), new Random().nextInt(999), 0, score_number);
        try (Session session = entityManager.unwrap(Session.class)) {
            session.save(card);
            return card.getCard_number();
        }
    }


    @Override
    public List<String> getListNumberCard() {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session
                    .createQuery("select card_number from Card", String.class)
                    .getResultList();
        }
    }


    @Override
    @Transactional
    public Float incrementBalanceFromCardNumber(String card_number, Float sumIncrement) throws NoResultException {
        try (Session session = entityManager.unwrap(Session.class)) {
            Query query = session.createNativeQuery("select * from Card where Card.card_number = :card_number", Card.class);
            Card card = (Card) query.setParameter("card_number", card_number).getSingleResult();
            card.setBalance(card.getBalance() + sumIncrement);
            session.saveOrUpdate(card);
            return card.getBalance();
        } catch (NoResultException e) {
            throw new NoResultException("Карты с данным номером не существует");
        }
    }


    @Override
    public Float getBalanceFromCardNumber(String card_number) throws NoResultException {
        try (Session session = entityManager.unwrap(Session.class)) {
            return session
                    .createQuery("from Card where card_number = :card_number", Card.class)
                    .setParameter("card_number", card_number)
                    .getSingleResult()
                    .getBalance();
        } catch (NoResultException e) {
            throw new NoResultException("Карты с таким номером нет");
        }
    }

}
