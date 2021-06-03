package com.example.bank.dao.legal;

import com.example.bank.entity.person.legal.LegalAccount;
import com.example.bank.entity.person.legal.LegalPerson;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Repository
public class LegalRepositoryImpl implements LegalRepository {

    @Autowired
    private EntityManager entityManager;

    public static BigInteger max_legal_score_number;

    @PostConstruct
    public void initMaxLegalScoreNumber() {
        Session session = entityManager.unwrap(Session.class);
        try {
            String current_legal_max_score_number = session.createQuery("select max(score_number) from LegalAccount ", String.class).getSingleResult();
            max_legal_score_number = new BigInteger(current_legal_max_score_number);
        } catch (NullPointerException e) {
            max_legal_score_number = new BigInteger("11111111112222222222");
        }
        session.close();
    }

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

    @Override
    public List<LegalPerson> getListLegalPerson() {
        try(Session session = entityManager.unwrap(Session.class)) {
            return session.createNativeQuery("select * from LEGAL_PERSON", LegalPerson.class)
                    .getResultList();
        }
    }


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
