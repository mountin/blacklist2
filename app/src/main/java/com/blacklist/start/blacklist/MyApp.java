package com.blacklist.start.blacklist;

import com.activeandroid.ActiveAndroid;

/**
 * Created by mountin on 09.06.2015.
 */
public class MyApp extends com.activeandroid.app.Application
{
    @Override
    public void onCreate()
    {        super.onCreate();
        ActiveAndroid.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}


