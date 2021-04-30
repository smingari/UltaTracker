package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class Reminder {

    private String name;
    private LocalDate date;
    private LocalTime time;
    private int key;
    private String desc;

    // Constructor method
    public Reminder(String name, LocalDate date, LocalTime time, String desc) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.desc = desc;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Reminder(String name, String date, String time, String desc, int key) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.desc = desc;
        this.key = key;
    }

    // Getter methods
    public String getName() { return name; }
    public String getDate() { return date.toString(); }
    public String getTime() { return time.toString(); }
    public String getDesc() { return desc; }
    public int getKey() { return key; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(LocalTime time) { this.time = time; }
    public void setDesc(String desc) { this.desc = desc; }

    // Generates random number for key
    public static int getRandomBetweenRange(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }

    public static Comparator <Reminder> reminderComparator = new Comparator<Reminder>() {
        @Override
        public int compare(Reminder o1, Reminder o2) {
            String rDate1 = o1.getDate();
            String rDate2 = o2.getDate();
            String rTime1 = o1.getTime();
            String rTime2 = o2.getTime();
            int dateCompare = rDate1.compareTo(rDate2);
            if(dateCompare == 0) {
                return rTime1.compareTo(rTime2);
            }
            else return dateCompare;
        }
    };



}
