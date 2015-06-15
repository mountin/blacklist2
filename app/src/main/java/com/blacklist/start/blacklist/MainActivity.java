package com.blacklist.start.blacklist;

//import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Iterator;
import java.util.List;

import model.NumberList;
import model.User;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CallReceiver asd = new CallReceiver(context, intent);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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

    public void saveToDb(View view) {
        NumberList Number = new NumberList();
        Number.number = "8888889900";
        Number.blockTimeType = 2;
        Number.unblockedUnixTime = 2;
        Number.save();
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    }

    public void selectFromDb(View view) {
        List Numbers = new Select().from(NumberList.class).execute();

        NumberList retrievedThing = null;
        Iterator<NumberList> i = Numbers.iterator();
        if(i.hasNext()){
            retrievedThing = i.next();
            Log.d("myApp", "number = " + retrievedThing.number + ", type = " + retrievedThing.blockTimeType);
        }

        Toast.makeText(this, "this is Toast! "
                , Toast.LENGTH_LONG).show();

//        if (Numbers.size()!=0) {
//            Toast.makeText(this, "name: " + Numbers.get(0).hashCode() + " age: "
//                    + Numbers, Toast.LENGTH_LONG).show();
//        }
    }

}
