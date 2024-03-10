package com.mobilestudy.myfridge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Product {
    private String name;
    private ExpirationDate date;

    public Product(String name, ExpirationDate date){
        this.name = name;
        this.date = date;
    }

    public ExpirationDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setDate(ExpirationDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toStringAll() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String startDateString = dateFormat.format(date.getStartExpirationDate());
        String endDateString = dateFormat.format(date.getEndExpirationDate());

        return "Name: " + name + ", Dates: " + startDateString + " to " + endDateString;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String startDateString = dateFormat.format(date.getStartExpirationDate());
        String endDateString = dateFormat.format(date.getEndExpirationDate());

        return name + ", ends: " + endDateString;
    }
}
