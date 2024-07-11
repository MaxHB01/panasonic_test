package com.panasonic.toughpad.android.sample.buttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import static android.content.Context.MODE_PRIVATE;

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences data = context.getSharedPreferences("data", MODE_PRIVATE);
        boolean isStarted = data.getBoolean("Start", false);
        if (isStarted) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                context.startService(new Intent(context, ButtonService.class));
            } else {
                context.startForegroundService(new Intent(context, ButtonService.class));
            }
        }
    }
}
