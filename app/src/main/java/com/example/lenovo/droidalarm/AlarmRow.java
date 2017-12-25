package com.example.lenovo.droidalarm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Lenovo on 03-Sep-17.
 */

public class AlarmRow extends RecyclerView.ViewHolder {

    TextView vtextClock;
    ToggleButton vtoggleButton;
    Context context;

    public AlarmRow(View itemView,Context context) {
        super(itemView);
        this.vtextClock = (TextView) itemView.findViewById(R.id.alarmtime);
        this.vtoggleButton = (ToggleButton)itemView.findViewById(R.id.onoff);
        this.context=context;
        //Log.e("Found the","View");
    }

    public void bindRow(Alarm alarm){
        this.vtextClock.setText(alarm.time);
        final boolean astate=alarm.state;
        final int mid=alarm.alarm_id;
        if(astate){
            vtoggleButton.setChecked(true);
        }else{
            vtoggleButton.setChecked(false);
        }
        vtoggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("OnClick","Set");
                if(astate){
                    vtoggleButton.setChecked(false);
                    ((BaseActivity)context).toggleOnOff(mid);
                }else{
                    vtoggleButton.setChecked(true);
                }
            }
        });
    }
}
