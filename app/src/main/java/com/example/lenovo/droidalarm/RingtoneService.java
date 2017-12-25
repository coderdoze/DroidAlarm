package com.example.lenovo.droidalarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Lenovo on 27-Aug-17.
 */

public class RingtoneService extends Service {

    private boolean ringingstate=false;
    private MediaPlayer ringtone;
    public static final int NOTIFICATION_ID=0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),0);

        ringtone=MediaPlayer.create(this,R.raw.numb);
        Boolean playstate=intent.getExtras().getBoolean(BaseActivity.ALARM_STATE);
        String label=intent.getExtras().getString(BaseActivity.ALARM_LABEL);
        Boolean dismissed=false;
        if(intent.hasExtra(AlarmOn.DISMISSED)){
            dismissed =intent.getExtras().getBoolean(AlarmOn.DISMISSED);
        }

        NotificationManager notificationManager=null;
        Notification notification;

        if(!(ringingstate) && playstate){


            ringtone.start();
            ringingstate=true;
            Log.e("Ringtone","Playing");
            //Setting up the intent for the Notification
            Intent notify=new Intent(this.getApplicationContext(),AlarmOn.class);
            notify.putExtra(BaseActivity.ALARM_LABEL,label);
            //Pending Intent
            PendingIntent pending_notify=PendingIntent.getActivity(this,0,notify,PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                pending_notify.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            //Setting up the NotificationManager

            notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            //Setting up the Notifiaction

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        notification=new Notification.Builder(this)
                            .setContentTitle("WAKE UP TIME")
                            .setContentText("Click ME")
                            .setContentIntent(pending_notify)
                            .setSmallIcon(R.drawable.ic_alarm_white_24dp)
                            .setLights(Color.GREEN,2000,2000)
                            .setVibrate(new long[]{3000,1000,3000,1000,3000})
                            .build();
                notification.flags=Notification.FLAG_ONGOING_EVENT;


            }else{
                        notification=new Notification.Builder(this)
                        .setContentTitle("WAKE UP TIME")
                        .setContentText("Click ME")
                        .setContentIntent(pending_notify)
                        .setSmallIcon(R.drawable.ic_alarm_white_24dp)
                                .setLights(Color.GREEN,2000,2000)
                                .setVibrate(new long[]{3000,1000,3000,1000,3000})
                        .getNotification();
                notification.flags=Notification.FLAG_ONGOING_EVENT;
            }

            notificationManager.notify(NOTIFICATION_ID,notification);

            /*Intent i=new Intent(this.getApplicationContext(),AlarmOn.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);*/
        }else if(ringingstate && !playstate){
            if(dismissed){
                notificationManager.cancel(NOTIFICATION_ID);
            }
            ringtone.stop();
            ringtone.reset();
            ringingstate=false;
        }

        return START_NOT_STICKY;
    }
}
