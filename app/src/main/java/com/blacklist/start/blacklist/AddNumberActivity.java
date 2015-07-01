package com.blacklist.start.blacklist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Date;
import java.util.TimeZone;

import model.NumberList;

/**
 * Created by mountin on 10.06.2015.
 */
public class AddNumberActivity extends Activity {

    static public String unblockedUnixTime;
    static public int blockTimeType;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnumber);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date d = new Date();

        // Check which radio button was clicked
        switch(view.getId()) {
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
                    AddNumberActivity.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 30 * 24 * 3600); // for 30 days
                    blockTimeType = 2;
                    break;
        }
        Log.d("asd", "Selected time is "+AddNumberActivity.unblockedUnixTime);
    }

    public void onClick(View view) {
        EditText phoneEditText = (EditText)findViewById(R.id.phone);

        Intent intent = new Intent(AddNumberActivity.this, StopListActivity.class);

        NumberList Number = new NumberList();

        Number.unblockedUnixTime = AddNumberActivity.unblockedUnixTime;


        Number.status = 1;
        Number.number = phoneEditText.getText().toString();
        Number.blockTimeType = AddNumberActivity.blockTimeType;
        Number.dateStart = String.valueOf((new Date().getTime() / 1000));


        Number.save();

        Toast.makeText(this, "Saved " + phoneEditText.getText().toString(), Toast.LENGTH_LONG).show();

        startActivity(intent);
    }
}
