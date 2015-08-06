package com.blacklist.start.blacklist;

import android.app.Activity;
import android.content.Intent;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;

import com.activeandroid.ActiveAndroid;

import model.GeneralBlackList;
import model.NumberList;
import model.User;

/**
 * Created by User on 08.07.2015.
 */

public class SplashScreen extends Activity {

    //how long until we go to the next activity
    protected int _splashTime = 500;

    private Thread splashTread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializeDB();
        setContentView(R.layout.splash);

        final SplashScreen sPlashScreen = this;

        // thread for displaying the SplashScreen
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized(this){

                        //wait 5 sec
                        wait(_splashTime);
                    }

                } catch(InterruptedException e) {}
                finally {
                    finish();

                    //start a new activity
                    Intent i = new Intent();
                    i.setClass(sPlashScreen, MainActivity.class);
                    startActivity(i);

                    finish();
                }
            }
        };

        splashTread.start();
    }

    //Function that will handle the touch
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized(splashTread){
                splashTread.notifyAll();
            }
        }
        return true;
    }

}