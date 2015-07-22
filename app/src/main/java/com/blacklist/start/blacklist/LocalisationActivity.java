package com.blacklist.start.blacklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by mountin on 21.07.2015.
 */
public class LocalisationActivity extends ActionBarActivity {
    private Locale locale = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //startService(new Intent(this, CheckService.class));
        setContentView(R.layout.activity_localisation);
    }

    public void changeLangRus(View view){
        changeLang("ru");
    }
    public void changeLangEng(View view){
        changeLang("en");
    }
    public void changeLang(String lang){

        Configuration config = getBaseContext().getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        String hello_world = getString(R.string.hello_world);
        Toast.makeText(this, hello_world, Toast.LENGTH_LONG).show();

        SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences(MainActivity.SPLANG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(MainActivity.SPLANG, lang);
        editor.commit();
        Log.d("saved local=", lang);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig,

                    getBaseContext().getResources().getDisplayMetrics());

        }
    }


}