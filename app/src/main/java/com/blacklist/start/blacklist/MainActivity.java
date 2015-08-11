package com.blacklist.start.blacklist;

//import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.cronService.AlarmReceiver;

import java.util.Locale;

import model.NumberList;

public class MainActivity extends BaseActivity {

    private MainActivity context;
    final public static int CHECKTIMER = 60000;//1 min
    private Locale locale = null;

    final static String SPLANG = "local";
    static String[] mChooseTime;// = {"24 часа", "7 дней", "", "1 час"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChooseTime = new String[] {getString(R.string.h24), getString(R.string.days7), getString(R.string.alltime), getString(R.string.h1)};

        //Get Preferenece localisation
        SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences(SPLANG, Context.MODE_PRIVATE);
        String localisatFromShare = sharedpreferences.getString(SPLANG, "");
        Log.d("asd", "localisation=" + localisatFromShare);


        if(localisatFromShare.isEmpty()){

                Intent intent = new Intent(MainActivity.this, LocalisationActivity.class);
                startActivity(intent);
                //finish();
        }

        //startService(new Intent(this, CheckService.class));
        setContentView(R.layout.activity_main);

        //CronService
        this.context = this;
        Intent alarm = new Intent(this.context, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        Log.d("asd", "Start Activity");
        if(alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), CHECKTIMER, pendingIntent);
        }else{
            Log.d("asd", "Alarm is olready running!!!");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void clearTable() {
        new Delete().from(NumberList.class).execute();
        Toast.makeText(this,"Table cleared!",Toast.LENGTH_LONG).show();
    }


    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, StopListActivity.class);
        startActivity(intent);

    }

    public void onClick2(View view) {

        Intent intent = new Intent(MainActivity.this, AddNumberActivity.class);
        startActivity(intent);
    }

    public void onClickShowLog(View view) {
        Intent intent = new Intent(MainActivity.this, LogListActivity.class);
        startActivity(intent);
    }


}
