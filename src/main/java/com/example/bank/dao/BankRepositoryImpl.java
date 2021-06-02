package com.example.bank.dao;

import com.example.bank.entity.physical_person.Card;
import com.example.bank.entity.legal_person.LegalAccount;
import com.example.bank.entity.legal_person.LegalPerson;
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
public class BankRepositoryImpl implements BankRepository {

    @Autowired
    private EntityManager entityManager;

    public static BigInteger max_card_number;

    public static BigInteger max_legal_score_number;


    /**
     * После создания бина полуачем самый большой номер карты (создание карт идет по возрастанию)
     * Ошибок быть не может, т.к мы просто ищем сумму
     */
    @PostConstruct
    public void init_max_card_number() {
        Session session = entityManager.unwrap(Session.class);
        try {
            String current_max_card_number = session.createQuery("select max(card_number) from Card", String.class).getSingleResult();
            max_card_number = new BigInteger(current_max_card_number);
        } catch (NullPointerException e) {
            max_card_number = new BigInteger("1111222233334444");
        }
        session.close();
    }


    /**
     * После создания бина полуачем самый большой номер счета у контрагентов (создание счета идет по возрастанию)
     * Ошибок быть не может, т.к мы просто ищем сумму
     */
    @PostConstruct
    public void init_max_legal_score_number() {
        Session session = entityManager.unwrap(Session.class);
        try {
            String current_legal_max_score_number = session.createQuery("select max(score_number) from LegalAccount ", String.class).getSingleResult();
            max_legal_score_number = new BigInteger(current_legal_max_score_number);
        } catch (NullPointerException e) {
            max_legal_score_number = new BigInteger("11111111112222222222");
        }
        session.close();
    }


    /**
     * Создает дату на 4 года вперед от сегодняшней
     */
    private Date getNewDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 4);
        return cal.getTime();
    }

    /**
     * Проверяем наличие номера СЧЁТА в бд
     */
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

    /**
     * Данный метод создает новую карту и возвращает её номер
     * Полностью рабочий
    **/
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

    /**
     * Получем список всех кредитных карт
     * Ошибок быть не может, т.к мы просто делаем селект всего столбца
     * Полностью рабочий
     **/
    @Override
    public List<String> getListNumberCard() {
        Session session = entityManager.unwrap(Session.class);
        return session
                .createQuery("select card_number from Card", String.class)
                .getResultList();
    }

    /**
     * Если номеро данной карты нет -> пробрасываем ошибку
     * Метод полностью рабочий
     */

    @Override
    @Transactional
    public Float incrementBalanceFromCardNumber(String card_number, Float sumIncrement) throws NoResultException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createNativeQuery("select * from Card where Card.card_number = :card_number", Card.class);
            Card card = (Card) query.setParameter("card_number", card_number).getSingleResult();
            card.setBalance(card.getBalance() + sumIncrement);
            session.saveOrUpdate(card);
            return card.getBalance();
        } catch (NoResultException e) {
            throw new NoResultException("Карты с данным номером не существует");
        }
    }

    /**
     * Метод получения баланса
     * Метод полностью рабочий
     */
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


//    <--------------------> 2 ЭТАП <-------------------->

    /**
     *  Создаем новое Юр.лицо
     *  Метод полностью рабочий
     */
    @Override
    @Transactional
    public LegalPerson addNewLegalPerson(String address, String type, String title) throws NoResultException {
        Session session = entityManager.unwrap(Session.class);
        try {
            session.createQuery("from LegalPerson where address = :address", LegalPerson.class)
                    .setParameter("address", address)
                    .getSingleResult();
        } catch (NoResultException e) {
            max_legal_score_number = max_legal_score_number.add(new BigInteger("1"));
            LegalAccount legalAccount = new LegalAccount(max_legal_score_number.toString(), address, 0);
            LegalPerson newPerson = new LegalPerson(address, type, title, Collections.singletonList(legalAccount));
            session.save(newPerson);
            session.close();
            return newPerson;
        }
        session.close();
        throw new NoResultException("Данное юридисеское лицо уже зарагестрировано");
    }


    /**
     * Получиние списка всех Юр.лиц
     * Метод полностью рабочий
     */
    @Override
    public List<LegalPerson> getListLegalPerson() {
        try(Session session = entityManager.unwrap(Session.class)) {
            return session.createNativeQuery("select * from LEGAL_PERSON", LegalPerson.class)
                    .getResultList();
        }
    }


    /**
     * Пополнить счет у Юр.лица
     * Метод полностью рабочий
     */
    @Override
    @Transactional
    public Float incrementBalanceFromLegalScore(String score_number, Float sumIncrement) throws NoResultException {
        Session session = entityManager.unwrap(Session.class);
        try {
            Query query = session.createQuery("from LegalAccount where score_number = : score_number", LegalAccount.class);
            LegalAccount account = (LegalAccount) query.setParameter("score_number", score_number).getSingleResult();
            account.setBalance(account.getBalance() + sumIncrement);
            session.saveOrUpdate(account);
            return account.getBalance();
        } catch (NoResultException e) {
            throw new NoResultException("Юридического лица с данным номером не существует");
        }
    }
}
