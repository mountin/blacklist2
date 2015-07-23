package com.blacklist.start.blacklist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by mountin on 21.07.2015.
 */
public class LocalisationActivity extends ActionBarActivity {
    private Locale locale = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_localisation);


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        //String strChoose = spinner.getSelectedItem().toString();
        //tv.setText(strChoose);

        //Log.d("asd", "selected=" + strChoose );

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.Language);
                String lang;
                switch (selectedItemPosition) {
                    case 0:
                        lang = "ru";
                        break;
                    case 1:
                        lang = "en";
                        break;
                    default:
                        lang = "en";
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + lang, Toast.LENGTH_SHORT);
                toast.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String lang;
//                switch (position) {
//                    case 0:
//                        lang = "ru";
//                        break;
//                    case 1:
//                        lang = "en";
//                        break;
//                    default:
//                        lang = "en";
//                }
//                Log.d("asd", "Saved lang=" + lang);
//                Log.d("asd", "setOnItemSelectedListener");
//
//
//                changeLang(lang);
//                Log.d("asd", "selected=" + selectedItemView + " position=" + position);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d("asd", "onNothingSelected");
//            }
//        });


    }

    public void changeLangRus(View view){
        changeLang("ru");
    }
    public void changeLangEng(View view){
        changeLang("en");
    }

    public void changeLangFromSelect(int position) {


        //changeLang(lang);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //home = 16908332
        if (id == 16908332) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}


