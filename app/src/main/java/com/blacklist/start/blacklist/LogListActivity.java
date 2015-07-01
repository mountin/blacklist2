package com.blacklist.start.blacklist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.CallLog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import model.NumberList;


public class LogListActivity extends ListActivity {

    private static int positionItem;
    private static NumberList number;
    Dialog dialog;

    private static int timeBlock;

    public static String currentItem;
    public static String selectedItem;

    private static final int CM_DELETE_ID = 1;
    public String[] catNamesArray = new String[]{};

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> callLogList;// = new ArrayList<>(Arrays.asList(catNamesArray));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //ListView listView = (ListView)findViewById(R.id.listView);

        this.callLogList = this.fetchInboxSms(2);
        if (this.callLogList != null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, callLogList);

            setListAdapter(mAdapter);


        } else {
            Toast.makeText(getApplicationContext(),
                    "The list is Empty, add one please ", Toast.LENGTH_SHORT).show();
        }


        setContentView(R.layout.activity_loglist);
    }

    @Override
    protected Dialog onCreateDialog(int id) {


        final String[] mChooseCats = {"24h", "7days", "AllTime"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                //.setMessage("Add number to block list?")
                .setTitle("Select time to block")
                .setCancelable(false)
                .setSingleChoiceItems(mChooseCats, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                LogListActivity.timeBlock = item;
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Select a time: "
                                                + mChooseCats[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                .setPositiveButton("Add",
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
                                        LogListActivity.number.unblockedUnixTime = String.valueOf((d.getTime() / 1000) + 30 * 24 * 3600); // for 30 days
                                        break;
                                }
                                Log.d("asd", "time=" + LogListActivity.number.dateStart);

                                LogListActivity.number.status = LogListActivity.timeBlock;
                                LogListActivity.number.save();

                                Toast.makeText(LogListActivity.this, "Saved new Item" + LogListActivity.number.unblockedUnixTime, Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(LogListActivity.this, StopListActivity.class);
                                startActivity(intent);

                                dialog.cancel();

                            }
                        })
                .setNegativeButton("Cancel!",
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
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        LogListActivity.positionItem = position;

        LogListActivity.number = new NumberList();

        LogListActivity.number.number = l.getItemAtPosition(position).toString();


        Toast.makeText(this, "Not Saved yet" + l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

        showDialog(2);


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
            message.messageContent = dir;
            message.messageDate = callDayTime.toString();
            smsInbox.add(message);
        }
        managedCursor.close();

        return smsInbox;

    }


}