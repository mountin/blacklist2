package com.blacklist.start.blacklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.TimeZone;

import model.NumberList;

/**
 * Created by mountin on 10.06.2015.
 */
public class AddNumberActivity extends ActionBarActivity {

    static public String unblockedUnixTime;
    static public int blockTimeType;
    static String phoneEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnumber);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date d = new Date();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton0:
                if (checked)
                    AddNumberActivity.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 3600); // for 1 hour
                blockTimeType = 3;
                break;
            case R.id.radioButton:
                if (checked)
                    AddNumberActivity.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 24 * 3600); // for 24 hours
                    blockTimeType = 0;
                    break;
            case R.id.radioButton2:
                if (checked)
                    AddNumberActivity.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 7 * 24 * 3600); // for 7 days
                    blockTimeType = 1;
                    break;
            case R.id.radioButton3:
                if (checked)
                    AddNumberActivity.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 9999 * 24 * 3600); // for 30 days
                    blockTimeType = 2;
                    break;
        }
        Log.d("asd", "Selected time is " + AddNumberActivity.unblockedUnixTime);
    }

    public boolean validator(){
        if(AddNumberActivity.unblockedUnixTime == null){
            Toast.makeText(this, "Выберите пожалуйста время!", Toast.LENGTH_LONG).show();
            return false;
        }

        EditText number = (EditText)findViewById(R.id.phone);

        this.phoneEditText = number.getText().toString().toString();


        Log.d("asd", "Selectedis = "+this.phoneEditText);

        if(this.phoneEditText.isEmpty()){
            Toast.makeText(this, "Укажите пожалуйста номер телефона!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!this.phoneEditText.matches("[0-9+]{3,20}")){
            Toast.makeText(this, "Введите пожалуйста корректный номер телефона! (например +12345678900)", Toast.LENGTH_LONG).show();
            return false;
        }


        return  true;
    }
    public void onClick(View view) {
        if(!this.validator()){
            return;
        }

        Intent intent = new Intent(AddNumberActivity.this, StopListActivity.class);

        NumberList Number = new NumberList();

        Number.unblockedUnixTime = AddNumberActivity.unblockedUnixTime;


        Number.status = 1;
        Number.number = this.phoneEditText;
        Number.blockTimeType = AddNumberActivity.blockTimeType;
        Number.dateStart = String.valueOf((new Date().getTime() / 1000));

        Log.d("asd", "Unix time is "+AddNumberActivity.unblockedUnixTime);


        try{
            Number.save();
        }catch (Exception e){
            Log.d("asd", e.toString());
        }


        Toast.makeText(this, "Сохранено " + this.phoneEditText, Toast.LENGTH_LONG).show();

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);

        MenuItem blockListItem = menu.findItem(R.id.action_compose);
        blockListItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    public void onSettingsMenuClick(MenuItem item) {
        TextView infoTextView = (TextView) findViewById(R.id.textViewInfo);
        infoTextView.setText("Вы выбрали пункт Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("asd", "Clicked="+item.getItemId());
        int id = item.getItemId();

        //home = 16908332
        if(id == 16908332){
            finish();
        }
        if (id == R.id.action_stoplist) {
            Intent intent = new Intent(AddNumberActivity.this, StopListActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.callList) {
            Intent intent = new Intent(AddNumberActivity.this, LogListActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_delete_all) {
            //this.clearTable();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
