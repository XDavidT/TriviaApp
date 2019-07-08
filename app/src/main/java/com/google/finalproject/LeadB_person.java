package com.google.finalproject;

public class LeadB_person {
    private String name, level;
    private String points;

    public LeadB_person(String name, String level, String points) {
        this.name = name;
        this.level = level;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return getName() + " got "+ getPoints()+" points in level:"+getLevel();
    }
}
