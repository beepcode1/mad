package com.example.medicinealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("MEDICINE_NAME");
        Toast.makeText(context, "Time to take medicine: " + medicineName, Toast.LENGTH_LONG).show();
    }
}
