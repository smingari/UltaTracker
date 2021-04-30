package com.example.ultratracker;

import java.time.LocalDate;
import java.util.HashMap;

public class Weight {


    private double weight;
    private LocalDate date;
    public Weight(double weight, LocalDate date){
        this.weight = weight;
        this.date = date;
    }

    public double getWeight(){ return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public LocalDate getDate(){ return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
