package com.example.lenovo.droidalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lenovo on 28-Aug-17.
 */

public class AlarmOn extends AppCompatActivity{
    Button dismiss=null;
    TextView showLabel=null;
    public static final String DISMISSED="dismiss clicked";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_on);

        dismiss=(Button)findViewById(R.id.dismiss);
        showLabel=(TextView)findViewById(R.id.showlabel);

        String label=getIntent().getExtras().getString(BaseActivity.ALARM_LABEL);
        showLabel.setText(label);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AlarmReceiver.class);
                i.putExtra(BaseActivity.ALARM_STATE,false);
                i.putExtra(DISMISSED,true);
                sendBroadcast(i);
                finish();
            }
        });
    }
}
