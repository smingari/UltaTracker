package com.example.ultratracker;

import java.time.LocalDate;
import java.util.List;

public class Workout {
    private String woName;
    private int key;
    private LocalDate date;
    private List<Weightlifting> liftList;

    public Workout(String woName, List<Weightlifting> list, String date, int key) {
        this.woName = woName;
        this.liftList = list;
        this.date = LocalDate.parse(date);
        this.key = key;
    }

    // Constructor with user input
    public Workout(String woName, List<Weightlifting> list, LocalDate date) {
        this.woName = woName;
        this.liftList = list;
        this.date = date;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Getters/Setters for each field
    public String getName() { return woName; }
    public void setName(String woName) { this.woName = woName; }
    public List<Weightlifting> getLiftList() { return liftList; }
    public void setLiftList(List<Weightlifting> liftList) { this.liftList = liftList; }
    public String getDate() { return date.toString(); }
    public void setDate(LocalDate date) { this.date = date; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}
