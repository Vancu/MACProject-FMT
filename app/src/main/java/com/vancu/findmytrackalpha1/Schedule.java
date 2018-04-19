package com.vancu.findmytrackalpha1;

import java.util.ArrayList;
import java.io.Serializable;

public class Schedule implements Serializable {
    String name;
    ArrayList<Stop> stops = new ArrayList<>();

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
