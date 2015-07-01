package com.cronService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cronService.BackgroundService;

/**
 * Created by mountin on 18.06.2015.
 */
public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundService.class));
    }
}