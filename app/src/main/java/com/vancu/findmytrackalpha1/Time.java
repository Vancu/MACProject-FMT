package com.vancu.findmytrackalpha1;

import java.io.Serializable;

import static com.google.android.gms.internal.zzbgp.NULL;

public class Time implements Serializable
{
    int hour;
    int minute;
    String stringTime;

    public Time() {
        hour = 25;
        minute = 0;
        stringTime = Integer.toString(hour) + ":" + Integer.toString(minute);
    }

    public Time(int hour, int minute) {
        if(hour >= 0 && hour <= 24) {
            this.hour = hour;
        }
        if(minute >=0 && minute <= 59) {
            this.minute = minute;
        }
        stringTime = Integer.toString(hour) + ":" + Integer.toString(minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getstringTime()
    {
        if(hour == 25) {
            return stringTime;
        }
        else {
            return stringTime = Integer.toString(hour) + ":" + Integer.toString(minute);
        }
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setStringTime(String stringTime) { this.stringTime = stringTime;   }

}
