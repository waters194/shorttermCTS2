package com.example.cts31301163.shorttermcts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Administrator on 2016/7/9.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent data) {
              startAlarm(context);
             Intent i = new Intent(context, AlarmAlert.class);
             Bundle bundleRet = new Bundle();
             bundleRet.putString("STR_CALLER", "");
             i.putExtras(bundleRet);
             i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             context.startActivity(i);


   Toast.makeText(context, "闹钟时间到了！", Toast.LENGTH_SHORT).show();

    }
    private void startAlarm(final Context context) {
        NotificationManager mgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification nt = new Notification();
        nt.defaults = Notification.DEFAULT_SOUND;
        int soundId = new Random(System.currentTimeMillis())
                .nextInt(Integer.MAX_VALUE);
        mgr.notify(soundId, nt);

    }



}