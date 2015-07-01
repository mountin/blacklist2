package com.cronService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.activeandroid.query.Select;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import model.NumberList;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("asd", "onCreate()!!!");
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }




    private Runnable myTask = new Runnable() {

        public void run() {
            Log.d("asd", "This is new Runnable() work!!!");
            ArrayList numbers = this.selectListFromDb();

            if(numbers != null){
                this.deleteOldUnixTimeItems(numbers, new Date());
            }

            System.out.println("Run servuce!!!");
            stopSelf();
        }


        private ArrayList<NumberList> selectListFromDb() {
            ArrayList NumberList = new Select().from(model.NumberList.class).execute();

            if (NumberList.size() != 0) {
                return NumberList;
            } else
                return null;
        }

        private void deleteOldUnixTimeItems(ArrayList<NumberList> paragems, Date d){

            Log.d("asd", "Lets Find some old numbers!!! now is  ="+d.getTime()/1000);
            for (NumberList p : paragems) {
                if (p.compare(p, d) == 1) {
                    p.delete();
                }
            }

        }


    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
        Log.d("asd", "This is new onDestroy() Stopped service!!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("asd", "onStartCommand!!!");
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}