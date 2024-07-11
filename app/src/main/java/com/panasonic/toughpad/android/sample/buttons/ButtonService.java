package com.panasonic.toughpad.android.sample.buttons;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import com.panasonic.toughpad.android.api.appbtn.AppButtonManager;
import com.panasonic.toughpad.android.sample.ApiTestListActivity;
import com.panasonic.toughpad.android.sample.R;

public class ButtonService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        //  If the user tab Start button => run service. Using shared preference to get value from fragment class.
        SharedPreferences data = context.getSharedPreferences("data", MODE_PRIVATE);
        boolean isStarted = data.getBoolean("Start", false);
        if (isStarted) {
            String title = getString(R.string.lbl_notif_title);
            String text = getString(R.string.lbl_notif_desc);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification;

            Intent i = new Intent(getBaseContext(), ApiTestListActivity.class);
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra(ApiTestListActivity.ACTION_ITEM_ID, "buttons");

            PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "channel_1";
                NotificationChannel channel = new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
                if (notificationManager == null){
                    return;
                }
                notificationManager.createNotificationChannel(channel);
                notification = new Notification.Builder(context, channelId)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.notif)
                        .setContentText(text)
                        .setContentIntent(pi)
                        .build();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(context)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.notif)
                        .setContentText(text)
                        .setContentIntent(pi)
                        .build();
            } else {
                notification = new Notification.Builder(context)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.notif)
                        .setContentText(text)
                        .setContentIntent(pi)
                        .getNotification();
            }
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = getApplicationContext();
        SharedPreferences data = context.getSharedPreferences("data", MODE_PRIVATE);
        boolean isStarted = data.getBoolean("Start", false);
        if (!isStarted) {
            stopSelf();
            return START_STICKY;
        }

        if (intent == null || intent.getAction() == null || !intent.getAction().equals(AppButtonManager.ACTION_APPBUTTON)) {
            // Ignore..
            return START_STICKY;
        }

        if (ButtonTestFragment.getInstance() != null) {
            ButtonTestFragment.getInstance().updateButtonState(intent);
        } else {
            Intent launchIntent = new Intent(getBaseContext(), ApiTestListActivity.class);
            launchIntent.setAction(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            launchIntent.putExtra(ApiTestListActivity.ACTION_ITEM_ID, "buttons");
            getApplication().startActivity(launchIntent);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
