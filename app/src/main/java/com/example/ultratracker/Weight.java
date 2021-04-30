package com.example.ultratracker;

import java.time.LocalDate;
import java.util.HashMap;

public class Weight{


    private double weight;
    private LocalDate date;

    public Weight(double weight, String date){
        this.weight = weight;
        this.date = LocalDate.parse(date);
    }
    public double getWeight(){ return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getDate(){ return date.toString(); }
    public void setDate(LocalDate date) { this.date = date; }
}
