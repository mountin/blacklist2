package com.blacklist.start.blacklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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


public class StopListActivity extends Activity {

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
        ListView listView = (ListView) findViewById(R.id.stop_list);


        StopListActivity.catNamesList = this.selectListFromDb();
        if (StopListActivity.catNamesList != null) {


            boxAdapter = new BoxAdapter(this, StopListActivity.catNamesList);

            listView.setAdapter(boxAdapter);
            listView.setOnItemClickListener(itemClickListener);


        } else {
            Toast.makeText(getApplicationContext(),
                    "The list is Empty, add one please ", Toast.LENGTH_SHORT).show();
        }


        Log.d("myApp", "show string");
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            StopListActivity.positionItem = position;

            StopListActivity.number = StopListActivity.catNamesList.get(position);
            Log.d("asd", "status=" + StopListActivity.number.status);
            showDialog(2);
//            Toast.makeText(getApplicationContext(),
//                    "You selected =" + position + ". on type=" + StopListActivity.number.status, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {

        final String[] mChooseCats = {"24h", "7days", "AllTime"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                //.setMessage("Add number to block list?")
                .setTitle("Select time to block")
                .setCancelable(false)
                .setSingleChoiceItems(mChooseCats, StopListActivity.number.status,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                StopListActivity.timeBlock = item;
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Select a time: "
                                                + mChooseCats[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int id) {

                                Log.d("asd", "positiion on listener:" + StopListActivity.positionItem);
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

                                currFromArray.status = StopListActivity.timeBlock;

                                currFromArray.save();

                                Toast.makeText(StopListActivity.this, "Saved new Item" + currFromArray.unblockedUnixTime, Toast.LENGTH_LONG).show();

                                boxAdapter.notifyDataSetChanged();

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

    public void onSettingsMenuClick(MenuItem item) {
        TextView infoTextView = (TextView) findViewById(R.id.textViewInfo);
        infoTextView.setText("Вы выбрали пункт Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView infoTextView = (TextView) findViewById(R.id.textViewInfo);
        int id = item.getItemId();
        infoTextView.setText("U have selected add phone!");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}