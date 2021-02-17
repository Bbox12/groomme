package com.zanrite.groomme.Alarm;


/**
 * Created by parag on 13/09/17.
 */

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by sonu on 09/04/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Stop sound service to play sound for alarm
        context.startService(new Intent(context, AlarmSoundService.class));

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

    }


}
