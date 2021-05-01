package com.example.ultratracker;

import java.time.LocalDate;
import java.util.HashMap;

public class Weight{


    private double weight;
    private LocalDate date;
    private int key;

    public Weight(double weight, String date){
        this.weight = weight;
        this.date = LocalDate.parse(date);
        this.key = getRandomBetweenRange(1, 100000);
    }
    public Weight(double weight, String date, int key){
        this.weight = weight;
        this.date = LocalDate.parse(date);
        this.key = key;
    }
    public double getWeight(){ return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public String getDate(){ return date.toString(); }
    public void setDate(LocalDate date) { this.date = date; }
    public int getKey(){ return key; }
    public void setKey(int key){ this.key = key; }
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}
