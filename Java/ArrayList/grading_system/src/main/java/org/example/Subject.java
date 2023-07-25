package org.example;

public class Subject {
    private String name;
    private int scorePoint;

    public Subject(String name, int scorePoint) {
        this.name = name;
        this.scorePoint = scorePoint;
    }

    public String getName() {
        return name;
    }

    public int getScorePoint() {
        return scorePoint;
    }
}
