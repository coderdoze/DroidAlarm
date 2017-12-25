package com.example.lenovo.droidalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lenovo on 27-Aug-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Log.e("Received The Broadcast ","For The Alarm");
        //Toast.makeText(context,"Game's On",Toast.LENGTH_LONG).show();

        Boolean state=intent.getExtras().getBoolean(BaseActivity.ALARM_STATE);
        String label=intent.getExtras().getString(BaseActivity.ALARM_LABEL);

        Intent ring=new Intent(context,RingtoneService.class);
        ring.putExtra(BaseActivity.ALARM_STATE,state);
        ring.putExtra(BaseActivity.ALARM_LABEL,label);
        context.startService(ring);

    }
}
