package com.blacklist.start.blacklist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class CheckService extends Service {
    public CheckService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск, выполнить какие-то действия.
        }
        else {
            // TODO Альтернативные действия в фоновом режиме.
        }

        String sms_body = intent.getExtras().getString("sms_body");
        //showNotification(sms_body);

        Log.d("asd", "onStartCommand check service");
       // someTask();


        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("asd", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
