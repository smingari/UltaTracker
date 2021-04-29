package com.example.ultratracker;

import java.time.LocalDate;
import java.util.Comparator;

public class Reminder {

    private String name;
    private LocalDate date;
    private int key;
    private String desc;

    // Constructor method
    public Reminder(String name, LocalDate date, String desc) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Reminder(String name, String date, String desc, int key) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.desc = desc;
        this.key = key;
    }

    // Getter methods
    public String getName() { return name; }
    public String getDate() { return date.toString(); }
    public String getDesc() { return desc; }
    public int getKey() { return key; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDesc(String desc) { this.desc = desc; }

    // Generates random number for key
    public static int getRandomBetweenRange(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }

    public static Comparator <Reminder> reminderComparator = new Comparator<Reminder>() {
        @Override
        public int compare(Reminder o1, Reminder o2) {
            String rTime1 = o1.getDate();
            String rTime2 = o2.getDate();

            return rTime1.compareTo(rTime2);
        }
    };



}
