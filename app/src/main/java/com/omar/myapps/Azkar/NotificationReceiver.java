package com.omar.myapps.Azkar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    /**
     * Broadcast reciever, starts when the device gets starts.
     * Start your repeating alarm here.
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.createNotification();

    }
}

