package com.vancu.findmytrackalpha1;

import java.util.Vector;

public class Schedule {
    String name;
    Vector<Stop> stops = new Vector<Stop>();

    public Schedule() {
    }

    public Schedule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStop(Stop stop){
        stops.add(stop);
    }


}
