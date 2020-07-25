package com.omar.myapps.Azkar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SampleBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            Utils.startAlarm(context);
        }

    }
}
