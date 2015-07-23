package com.blacklist.start.blacklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by mountin on 21.07.2015.
 */
public class LocalisationActivity extends ActionBarActivity {
    private Locale locale = null;

    public static String[] choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_localisation);

        choose = getResources().getStringArray(R.array.Language);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        //Get Preferenece localisation
        SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences(MainActivity.SPLANG, Context.MODE_PRIVATE);
        String localisatFromShare = sharedpreferences.getString(MainActivity.SPLANG, "");
        Log.d("asd", "localisation=" + localisatFromShare);

        // Настраиваем адаптер
        MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.spinner_langrow, choose);


        if(localisatFromShare.isEmpty()){
             if(!Locale.getDefault().getLanguage().equals("ru")){
                 localisatFromShare = "en";
            }
        }
        int localSavedPosition = localisatFromShare.equals("en") ? 1 : 0;


        // Вызываем адаптер
        spinner.setAdapter(adapter);
        spinner.setSelection(localSavedPosition);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

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
                changeLang(lang);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void changeLangRus(View view) {
        changeLang("ru");
    }

    public void changeLangEng(View view) {
        changeLang("en");
    }


    public void changeLang(String lang) {

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


    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
            Log.d("asd", "languages" + choose);
        }


        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_langrow, parent, false);
            TextView label = (TextView) row.findViewById(R.id.langitem);
            label.setText(choose[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.icon);

            if (position == 1) {
                icon.setImageResource(R.mipmap.flag2);
            } else {
                icon.setImageResource(R.mipmap.flag);
            }
            return row;
        }
    }

}


