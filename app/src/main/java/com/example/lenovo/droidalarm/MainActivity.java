package com.example.lenovo.droidalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText label;
    public static int alarm_id=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker)findViewById(R.id.timepicker);
        label=(EditText)findViewById(R.id.label);

        Button set_alarm=(Button)findViewById(R.id.set_alarm);
        Button cancel_alarm=(Button)findViewById(R.id.cancel_alarm);

        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar=Calendar.getInstance();
                String aLabel=label.getText().toString();

                int mHour;
                int mMinutes;
                mHour = timePicker.getCurrentHour();
                mMinutes = timePicker.getCurrentMinute();

                calendar.set(Calendar.HOUR_OF_DAY,mHour);
                calendar.set(Calendar.MINUTE,mMinutes);
                String time=String.valueOf(mHour)+":"+String.valueOf(mMinutes);
                Toast.makeText(MainActivity.this,time,Toast.LENGTH_LONG).show();

                //Return Intent to the Base Activity with the details of the Alarm to be displayed in the list.
                Intent returnIntent=new Intent();

                returnIntent.putExtra("Calendar",calendar);
                returnIntent.putExtra(BaseActivity.ALARM_ID,alarm_id);
                returnIntent.putExtra(BaseActivity.ALARM_TIME,time);
                returnIntent.putExtra(BaseActivity.ALARM_STATE,true);
                returnIntent.putExtra(BaseActivity.ALARM_LABEL,aLabel);
                setResult(Activity.RESULT_OK,returnIntent);

                alarm_id++;
                //Return Intent
                finish();
            }
        });

        cancel_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Just Return to the  base Activity
               finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
