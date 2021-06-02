package com.example.bank.entity.physical_person;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "people")
public class People {

    @Id
    @Column(name = "passport")
    private String passport;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    private Date date;

//    @OneToMany(targetEntity = Physical_account.class,
//            fetch = FetchType.EAGER)
//    @JoinColumn(name = "passport")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "passport")
    private List<Score> physical_account;

    public People() {

    }

    public People(String passport, String name, String surname, String patronymic, Date date, List<Score> physical_account) {
        this.passport = passport;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.date = date;
        this.physical_account = physical_account;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Score> getPhysical_account() {
        return physical_account;
    }

    public void setPhysical_account(List<Score> physical_account) {
        this.physical_account = physical_account;
    }

    @Override
    public String toString() {
        return "People{" +
                "passport='" + passport + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", date=" + date +
                ", physical_account=" + physical_account +
                '}';
    }
}
