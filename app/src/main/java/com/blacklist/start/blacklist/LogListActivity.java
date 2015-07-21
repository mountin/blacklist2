package com.blacklist.start.blacklist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.CallLog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import model.NumberList;
import model.NumberListInterface;

public class LogListActivity extends ActionBarActivity {

    private static int positionItem;
    private static NumberList number;
    Dialog dialog;

    private static int timeBlock;

    public static String currentItem;
    public static String selectedItem;

    private static final int CM_DELETE_ID = 1;
    public String[] catNamesArray = new String[]{};

    private static ArrayList<Message> callLogList;
    BoxAdapterLogList boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loglist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.list);

        this.callLogList = this.fetchInboxSms(2);


        if (this.callLogList != null) {
            boxAdapter = new BoxAdapterLogList(this, this.callLogList);
            listView.setAdapter(boxAdapter);
            listView.setOnItemClickListener(itemClickListener);
        } else {
            listView.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.emptyList), Toast.LENGTH_SHORT).show();
        }

    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            LogListActivity.positionItem = position;
            //LogListActivity.number = LogListActivity.callLogList.get(position);
            Log.d("asd", "AdapterView.OnItemClickListener Selected position is =" + position);
            LogListActivity.positionItem = position;

            LogListActivity.number = new NumberList();

            LogListActivity.number.number = LogListActivity.callLogList.get(position).toString();

            Log.d("asd", "U selected number  is =" + LogListActivity.number.number);

            Toast.makeText(LogListActivity.this, getString(R.string.notSaved) + LogListActivity.number.number, Toast.LENGTH_LONG).show();

            showDialog(2);

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                //.setMessage("Add number to block list?")
                .setTitle(getString(R.string.SelecTime))
                .setCancelable(false)
                .setSingleChoiceItems(MainActivity.mChooseTime, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                LogListActivity.timeBlock = item;
                                Toast.makeText(
                                        getApplicationContext(),
                                        getString(R.string.SelecedTime)
                                                + MainActivity.mChooseTime[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton(getString(R.string.Add),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int id) {

                                Log.d("asd", "positiion on listener:" + LogListActivity.positionItem);
                                TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
                                Date d = new Date();

                                LogListActivity.number.dateStart = String.valueOf((d.getTime() / 1000));

                                switch (LogListActivity.timeBlock) {
                                    case 0:
                                        LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 24 * 3600); // for 24 hours
                                        //LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) ); // for now!
                                        break;
                                    case 1:
                                        LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 7 * 24 * 3600); // for 7 days
                                        break;
                                    case 2:
                                        LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 9999 * 24 * 3600); // for 30 days
                                        break;
                                    case 3:
                                        LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 3600); // for 1 hour
                                        break;
                                }
                                Log.d("asd", "time=" + LogListActivity.number.dateStart);

                                LogListActivity.number.status = LogListActivity.timeBlock;
                                LogListActivity.number.save();

                                Toast.makeText(LogListActivity.this, getString(R.string.Saved) + LogListActivity.number.number, Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(LogListActivity.this, StopListActivity.class);
                                startActivity(intent);

                                dialog.cancel();

                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, getString(R.string.Delete));
    }

    //@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);

        Log.d("asd", "onListItemClick Clicked is position=" + position);
        LogListActivity.positionItem = position;

        LogListActivity.number = new NumberList();

        LogListActivity.number.number = l.getItemAtPosition(position).toString();

        Toast.makeText(this, getString(R.string.notSaved) + l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

        showDialog(2);

    }

    public void onSettingsMenuClick(MenuItem item) {
//        TextView infoTextView = (TextView) findViewById(R.id.textViewInfo);
//        infoTextView.setText("Вы выбрали пункт Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //home = 16908332
        if (id == 16908332) {
            finish();
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList fetchInboxSms(int type) {
        ArrayList<Message> smsInbox = new ArrayList<Message>();

        Cursor managedCursor = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type1 = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type1);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            Message message = new Message();
            message.messageNumber = phNumber;
            message.number = phNumber;
            message.messageContent = dir;
            message.messageDate = callDayTime;
            smsInbox.add(message);
        }
        managedCursor.close();
        return smsInbox;
    }

}