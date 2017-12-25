package com.example.lenovo.droidalarm;

/**
 * Created by Lenovo on 03-Sep-17.
 */

public class Alarm {

    String time;
    boolean state;
    int alarm_id;
    String label;

    public Alarm(String time, boolean state, int id,String label) {
        this.time = time;
        this.state = state;
        this.alarm_id=id;
        this.label=label;
    }
}
