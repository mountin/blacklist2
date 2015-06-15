package com.blacklist.start.blacklist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.NumberList;

/**
 * Created by mountin on 10.06.2015.
 */
public class AddNumberActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnumber);

    }
    public void onClick(View view) {
        EditText phoneEditText = (EditText)findViewById(R.id.phone);
        EditText blockTimeEditText = (EditText) findViewById(R.id.blocktime);

        Intent intent = new Intent(AddNumberActivity.this, StopListActivity.class);

        NumberList Number = new NumberList();
        Number.number = phoneEditText.getText().toString();
        //Number.blockTimeType = Integer.parseInt(blockTimeEditText.getText().toString()); //in int
        Number.save();

        Toast.makeText(this, "Saved" + phoneEditText.getText().toString(), Toast.LENGTH_LONG).show();

        //to transfer data beetwenactivities
        //intent.putExtra("phone", phoneEditText.getText().toString());
        //intent.putExtra("time", blockTimeEditText.getText().toString());

        startActivity(intent);
    }
}
