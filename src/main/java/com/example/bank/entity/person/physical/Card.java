package com.example.bank.entity.person.physical;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "card_number")
    private String card_number;

    @Column(name = "validity")
    private Date validity;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "balance")
    private float balance;

    @Column(name = "score_number")
    private String score_number;

    public Card() {

    }

    public Card(String card_number, Date validity, int cvv, float balance, String score_number) {
        this.card_number = card_number;
        this.validity = validity;
        this.cvv = cvv;
        this.balance = balance;
        this.score_number = score_number;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getScore_number() {
        return score_number;
    }

    public void setScore_number(String score_number) {
        this.score_number = score_number;
    }

    @Override
    public String toString() {
        return "Card{" +
                "card_number='" + card_number + '\'' +
                ", validity=" + validity +
                ", cvv=" + cvv +
                ", balance=" + balance +
                ", score_number='" + score_number + '\'' +
                '}';
    }
}
