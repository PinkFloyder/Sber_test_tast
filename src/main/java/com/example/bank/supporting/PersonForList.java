package com.example.bank.supporting;

import java.util.List;

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
}
