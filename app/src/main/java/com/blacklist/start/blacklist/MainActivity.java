package com.blacklist.start.blacklist;

//import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cronService.AlarmReceiver;

import java.util.Iterator;
import java.util.List;

import model.NumberList;
import model.User;


public class MainActivity extends ActionBarActivity {

    private MainActivity context;
    final public static int CHECKTIMER = 60000;//1 min

    static final String[] mChooseTime = {"24 часа", "7 дней", "Навсегда", "1 час"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_stoplist) {
            Intent intent = new Intent(MainActivity.this, StopListActivity.class);

            startActivity(intent);
            Log.d("asd", "clicked Search1");
            return true;
        }

        if (id == R.id.callList) {
            Intent intent = new Intent(MainActivity.this, LogListActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_compose) {
            Intent intent = new Intent(MainActivity.this, AddNumberActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete_all) {
            this.clearTable();
            return true;
        }


        return super.onOptionsItemSelected(item);
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
