package com.blacklist.start.blacklist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

import model.NumberList;


public class StopListActivity extends BaseActivity {

    private static int positionItem;
    private static int timeBlock;
    private static NumberList number;
    public String[] catNamesArray = new String[]{};

    private ArrayAdapter<String> mAdapter;
    public static ArrayList<NumberList> numbersFromStopList;

    BoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoplist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ListView listView = (ListView) findViewById(R.id.stop_list);

        NumberList numInstance = new NumberList();

        StopListActivity.numbersFromStopList = numInstance.selectListFromDb();
        if (StopListActivity.numbersFromStopList != null) {

            boxAdapter = new BoxAdapter(this, StopListActivity.numbersFromStopList);

            listView.setAdapter(boxAdapter);
            listView.setOnItemClickListener(itemClickListener);

        } else {
            listView.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.emptyList), Toast.LENGTH_SHORT).show();
            //listView.setBackground(Drawable.createFromPath("@mipmap/ic_phone"));
        }
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            StopListActivity.positionItem = position;
            StopListActivity.number = StopListActivity.numbersFromStopList.get(position);
            Log.d("asd", "Open Dialog blockTimeType=" + StopListActivity.number.blockTimeType);
            Log.d("asd", "Selected position is =" + StopListActivity.number.blockTimeType + " number=" + StopListActivity.number.number);

            showDialog(2);

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {

        Log.d("asd", "some id=" + id);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                //.setMessage("Add number to block list?")
                .setTitle(getString(R.string.SelecTime))
                .setCancelable(true)
                .setSingleChoiceItems(MainActivity.mChooseTime, StopListActivity.number.blockTimeType,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {

                                StopListActivity.timeBlock = item;
                                StopListActivity.number.blockTimeType = item;
                                Log.d("asd", "New !!! selected position blockTimeType=" + StopListActivity.number.blockTimeType);
                                //Toast.makeText(getApplicationContext(),getString(R.string.SelectTime)+" "+ MainActivity.mChooseTime[item], Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton(getString(R.string.Save),
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
                                        currFromArray.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 9999 * 24 * 3600); // for 30 days
                                        break;
                                    case 3:
                                        currFromArray.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 3600); // for 1 hour
                                        break;
                                }

                                Log.d("asd", "Saved blockTimeType: is:" + StopListActivity.number.blockTimeType);

                                currFromArray.save();

                                Toast.makeText(StopListActivity.this, getString(R.string.Saved)+ " " + currFromArray.number, Toast.LENGTH_LONG).show();

                                boxAdapter.notifyDataSetChanged();
                                boxAdapter.notifyDataSetInvalidated();

                                dialog.cancel();
                                //removeDialog(2);
                            }
                        })
                .setNegativeButton(getString(R.string.Delete),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                StopListActivity.number.delete();

                                StopListActivity.numbersFromStopList.remove(StopListActivity.positionItem);

                                Toast.makeText(StopListActivity.this, getString(R.string.Deleted), Toast.LENGTH_LONG).show();

                                boxAdapter.notifyDataSetChanged();

                                dialog.cancel();
                                //removeDialog(2);
                            }
                        })
                .setOnCancelListener(new Dialog.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Log.d("asd", "This is Cancel");
                        removeDialog(2);
                    }
                });

        return builder.create();

    }


    public void onDismiss()  {
        Log.d("asd", "This is onDismiss");
        removeDialog(2);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        Log.d("asd", "this is onpreparedialog id= "+id);
    }


}