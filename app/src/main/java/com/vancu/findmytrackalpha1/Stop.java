package com.vancu.findmytrackalpha1;

import java.util.Vector;

public class Stop
{
    String name;
    Vector<Time> times = new Vector<>();

    public Stop() {
    }

    public Stop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTime(Time time){
        times.add(time);
    }

    public void addTime(int hour, int min){
        Time time = new Time(hour,min);
        times.add(time);
    }
}
