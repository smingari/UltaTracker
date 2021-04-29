package com.example.ultratracker;

import java.time.LocalDate;
import java.util.Comparator;

public class Note {
    private String name;
    private LocalDate date;
    private int key;
    private String desc;

    // Constructor method
    public Note(String name, LocalDate date, String desc) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.key = getRandomBetweenRange(1, 100000);
    }

    public Note(String name, String date, String desc, int key) {
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
    public static int getRandomBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return (int) x;
    }

    public static Comparator<Note> reminderComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            String nTime1 = o1.getDate();
            String nTime2 = o2.getDate();

            return nTime1.compareTo(nTime2);
        }
    };

}
