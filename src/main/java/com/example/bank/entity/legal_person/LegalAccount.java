package com.example.bank.entity.legal_person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "legal_account")
public class LegalAccount {

    @Id
    @Column(name = "score_number")
    private String score_number;

    @Column(name = "address")
    private String address;

    @Column(name = "balance")
    private float balance;

    public LegalAccount() {

    }

    public LegalAccount(String score_number, String address, float balance) {
        this.score_number = score_number;
        this.address = address;
        this.balance = balance;
    }

    public String getScore_number() {
        return score_number;
    }

    public void setScore_number(String score_number) {
        this.score_number = score_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalAccount that = (LegalAccount) o;
        return Float.compare(that.balance, balance) == 0 && Objects.equals(score_number, that.score_number) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score_number, address, balance);
    }
}
