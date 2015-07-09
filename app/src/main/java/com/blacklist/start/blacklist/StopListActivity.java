package com.blacklist.start.blacklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import android.util.Log;

import model.NumberList;


public class StopListActivity extends ActionBarActivity {

    private static int positionItem;
    private static int timeBlock;
    private static NumberList number;
    public String[] catNamesArray = new String[]{};

    private ArrayAdapter<String> mAdapter;
    private static ArrayList<NumberList> catNamesList;

    BoxAdapter boxAdapter;

    public ArrayList selectListFromDb() {
        ArrayList NumberList = new Select().from(model.NumberList.class).execute();

        if (NumberList.size() != 0) {
            return NumberList;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoplist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ListView listView = (ListView) findViewById(R.id.stop_list);


        StopListActivity.catNamesList = this.selectListFromDb();
        if (StopListActivity.catNamesList != null) {


            boxAdapter = new BoxAdapter(this, StopListActivity.catNamesList);

            listView.setAdapter(boxAdapter);
            listView.setOnItemClickListener(itemClickListener);


        } else {
            Toast.makeText(getApplicationContext(),
                    "The list is Empty, add one please ", Toast.LENGTH_SHORT).show();
            //listView.setBackground(Drawable.createFromPath("@mipmap/ic_phone"));
        }


        Log.d("myApp", "show string");
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            StopListActivity.positionItem = position;

            StopListActivity.number = StopListActivity.catNamesList.get(position);
            Log.d("asd", "Open Dialog blockTimeType=" + StopListActivity.number.blockTimeType);
            showDialog(2);

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {

        final String[] mChooseCats = {"24h", "7days", "AllTime"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                //.setMessage("Add number to block list?")
                .setTitle("Select time to block")
                .setCancelable(true)
                .setSingleChoiceItems(mChooseCats, StopListActivity.number.blockTimeType,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {

                                //StopListActivity.timeBlock = item;
                                StopListActivity.number.blockTimeType = item;
                                Log.d("asd", "New !!! selected position blockTimeType=" + StopListActivity.number.blockTimeType);
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Select a time: "
                                                + mChooseCats[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int id) {

                                Log.d("asd", "positiion on listener: " + StopListActivity.positionItem);
                                TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                                Date d = new Date();

                                NumberList currFromArray = StopListActivity.number;

                                currFromArray.dateStart = String.valueOf((d.getTime() / 1000));

                                switch (StopListActivity.timeBlock) {
                                    case 0:
                                        currFromArray.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 24 * 3600); // for 24 hours
                                        break;
                                    case 1:
                                        currFromArray.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 7 * 24 * 3600); // for 7 days
                                        break;
                                    case 2:
                                        currFromArray.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 30 * 24 * 3600); // for 30 days
                                        break;
                                }

                                //currFromArray.blockTimeType = StopListActivity.timeBlock;
                                Log.d("asd", "Saved blockTimeType: " + currFromArray.blockTimeType);

                                currFromArray.save();

                                Toast.makeText(StopListActivity.this, "Saved new Item" + currFromArray.number, Toast.LENGTH_LONG).show();

                                boxAdapter.notifyDataSetChanged();
                                boxAdapter.notifyDataSetInvalidated();

                                dialog.cancel();

                            }
                        })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                StopListActivity.number.delete();

                                StopListActivity.catNamesList.remove(StopListActivity.positionItem);

                                Toast.makeText(StopListActivity.this, "Item has deleted!", Toast.LENGTH_LONG).show();

                                boxAdapter.notifyDataSetChanged();

                                dialog.cancel();
                            }
                        });


        return builder.create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onSettingsMenuClick(MenuItem item) {
        TextView infoTextView = (TextView) findViewById(R.id.textViewInfo);
        infoTextView.setText("Вы выбрали пункт Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("asd", "Clicked="+item.getItemId());

        //home = 16908332
        int id = item.getItemId();
        if (id == R.id.action_stoplist) {
            Intent intent = new Intent(StopListActivity.this, StopListActivity.class);

            startActivity(intent);

            return true;
        }

        if (id == R.id.callList) {
            Intent intent = new Intent(StopListActivity.this, LogListActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_compose) {
            Intent intent = new Intent(StopListActivity.this, AddNumberActivity.class);
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