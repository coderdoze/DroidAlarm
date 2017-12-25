package com.example.lenovo.droidalarm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Lenovo on 03-Sep-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<AlarmRow> {

    ArrayList<Alarm> alarmArrayList=new ArrayList<>();
    Context mContext;

    public RecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public AlarmRow onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item,parent,false);
        //Log.e("View","Inflated");
        return new AlarmRow(inflatedView,mContext);
    }

    @Override
    public void onBindViewHolder(AlarmRow holder, int position) {
        if(alarmArrayList.size()!=0) {
            Alarm alarmobj = alarmArrayList.get(position);
            holder.bindRow(alarmobj);
            //Log.e("Row", "Binded");
        }
    }

    @Override
    public int getItemCount() {
        return alarmArrayList.size();
    }

    public void newAlarmInserted(String timeOfAlarm,boolean alarmState,int alarmId,String alarmLabel){
        alarmArrayList.add(new Alarm(timeOfAlarm,alarmState,alarmId,alarmLabel));
        notifyDataSetChanged();
        //Log.e("New Alram ","Inserted");
    }

}
