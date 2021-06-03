package com.example.bank.entity.person.physical;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "score")
public class Score {

    @Column(name = "passport")
    private String passport;

    @Id
    @Column(name = "score_number")
    private String score_number;

//    @OneToMany(targetEntity = Card.class,
//            fetch = FetchType.EAGER)
//    @JoinColumn(name = "number_score")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "score_number")
    private List<Card> cards;

    public Score() {

    }

    public Score(String passport, String score_number, List<Card> cards) {
        this.passport = passport;
        this.score_number = score_number;
        this.cards = cards;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getScore_number() {
        return score_number;
    }

    public void setScore_number(String score_number) {
        this.score_number = score_number;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Physical_account{" +
                "passport='" + passport + '\'' +
                ", score_number='" + score_number + '\'' +
                ", cards=" + cards +
                '}';
    }
}
