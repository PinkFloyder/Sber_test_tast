package com.example.bank.entity.person.legal;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "legal_person")
public class LegalPerson {

    @Id
    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address", fetch = FetchType.EAGER)
    private List<LegalAccount> accounts;

    public LegalPerson() {

    }

    public LegalPerson(String address, String type, String title, List<LegalAccount> accounts) {
        this.address = address;
        this.type = type;
        this.title = title;
        this.accounts = accounts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LegalAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<LegalAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalPerson that = (LegalPerson) o;
        return Objects.equals(address, that.address) && Objects.equals(type, that.type) && Objects.equals(title, that.title) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, type, title, accounts);
    }
}
