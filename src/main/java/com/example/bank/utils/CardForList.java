package com.example.bank.utils;


import java.util.Objects;

public class CardForList {

    private int id;
    private String number;

    public CardForList() {
    }

    public CardForList(int id, String number) {
        this.id = id;
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(number, i * 4, (i + 1) * 4).append(" ");
        }
        this.number = stringBuilder.toString().trim();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardForList that = (CardForList) o;
        return id == that.id && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
