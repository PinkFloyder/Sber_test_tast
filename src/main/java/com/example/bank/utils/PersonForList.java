package com.example.bank.utils;

import java.util.List;
import java.util.Objects;

public class PersonForList {

    private String title;

    private List<String> score_number;

    public PersonForList() {

    }

    public PersonForList(String title, List<String> score_number) {
        this.title = title;
        this.score_number = score_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getScore_number() {
        return score_number;
    }

    public void setScore_number(List<String> score_number) {
        this.score_number = score_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonForList that = (PersonForList) o;
        return Objects.equals(title, that.title) && Objects.equals(score_number, that.score_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, score_number);
    }
}
