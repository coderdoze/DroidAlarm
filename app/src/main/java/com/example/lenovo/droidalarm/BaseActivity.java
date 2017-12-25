package com.example.lenovo.droidalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;

import java.util.Calendar;

/**
 * Created by Lenovo on 02-Sep-17.
 */

public class BaseActivity extends AppCompatActivity {

    //Instance Variables
    public static final int REQUEST_CODE=1;
    public static final String ALARM_TIME="AlarmTime";
    public static final String ALARM_ID="AlarmId";
    public static final String ALARM_STATE="alarm_state";
    public static final String ALARM_LABEL="alarm_label";

    private AlarmManager alarmManager;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager  mLayoutManager;
    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //Setting up the recycler view
        mRecyclerView=(RecyclerView) findViewById(R.id.recview);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerAdapter=new RecyclerAdapter(this);
        if(mRecyclerView!=null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        FloatingActionButton mfab=(FloatingActionButton)findViewById(R.id.fabbt);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BaseActivity.this,MainActivity.class);
                startActivityForResult(i,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){

                //Sending the Pending Intent For The Alarm
                Calendar calendar=(Calendar)data.getExtras().get("Calendar");
                int aid=data.getExtras().getInt(ALARM_ID);
                String atime=data.getExtras().getString(ALARM_TIME);
                boolean astate=data.getExtras().getBoolean(ALARM_STATE);
                String aLabel=data.getExtras().getString(ALARM_LABEL);

                Intent call=new Intent(BaseActivity.this,AlarmReceiver.class);
                //Adding the Alarm State to the Intent
                call.putExtra(BaseActivity.ALARM_STATE,true);
                call.putExtra(BaseActivity.ALARM_LABEL,aLabel);

                PendingIntent pendingIntent=PendingIntent.getBroadcast(BaseActivity.this,aid,call,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        pendingIntent);

                //Inform the Adapter about the new Alarm
                mRecyclerAdapter.newAlarmInserted(atime,true,aid,aLabel);
            }
        }
    }

    public void toggleOnOff(int id){
        Intent i=new Intent(BaseActivity.this,AlarmReceiver.class);
        if(PendingIntent.getBroadcast(BaseActivity.this,id,i,PendingIntent.FLAG_NO_CREATE)!=null){
            PendingIntent pendingIntent=PendingIntent.getBroadcast(BaseActivity.this,id,i,PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
            for(Alarm a:mRecyclerAdapter.alarmArrayList){
                if(a.alarm_id==id){
                    a.state=false;
                }
            }

        }
    }
}
