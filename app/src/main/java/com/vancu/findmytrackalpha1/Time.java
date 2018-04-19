package com.vancu.findmytrackalpha1;

import java.io.Serializable;

public class Time implements Serializable
{
    int hour;
    int minute;

    public Time() {
        hour = 0;
        minute = 0;
    }

    public Time(int hour, int minute) {
        if(hour >= 0 && hour <= 24) {
            this.hour = hour;
        }
        if(minute >=0 && minute <= 59) {
            this.minute = minute;
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
