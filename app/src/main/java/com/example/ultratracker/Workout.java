package com.example.ultratracker;

import java.time.LocalDate;
import java.util.List;

public class Workout {
    private String woName;
    private int key, sets, reps;
    private LocalDate date;
    private List<Weightlifting> liftList;

    public Workout(String woName, List<Weightlifting> list, String date, int key) {
        this.woName = woName;
        this.liftList = list;
        this.sets = 0;
        this.reps = 0;
        this.date = LocalDate.parse(date);
        this.key = key;
    }

    // Constructor for workout retrieval
    public Workout(String woName, List<Weightlifting> list, int sets, int reps, String date, int key) {
        this.woName = woName;
        this.liftList = list;
        this.sets = sets;
        this.reps = reps;
        this.date = LocalDate.parse(date);
        this.key = key;
    }

    // Constructor with user input
    public Workout(String woName, List<Weightlifting> list, LocalDate date) {
        this.woName = woName;
        this.liftList = list;
        this.sets = 0;
        this.reps = 0;
        this.date = date;
        this.key = getRandomBetweenRange(1, 100000);
    }

    // Getters/Setters for each field
    public String getName() { return woName; }
    public void setName(String woName) { this.woName = woName; }
    public List<Weightlifting> getLiftList() { return liftList; }
    public void setLiftList(List<Weightlifting> liftList) { this.liftList = liftList; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public String getDate() { return date.toString(); }
    public void setDate(LocalDate date) { this.date = date; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }
}
