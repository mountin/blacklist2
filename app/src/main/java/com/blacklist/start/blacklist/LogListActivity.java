package com.blacklist.start.blacklist;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.CallLog;
import android.text.TextUtils;
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

import model.NumberList;


public class LogListActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    private static final int CM_DELETE_ID = 1;
    public String[] catNamesArray = new String[]{ };

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> catNamesList;// = new ArrayList<>(Arrays.asList(catNamesArray));

    public  ArrayList selectListFromDb() {
        ArrayList NumberList = new Select().from(model.NumberList.class).execute();

        if (NumberList.size()!=0) {
            return NumberList;
        }else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        this.catNamesList = this.fetchInboxSms(2);
    if(this.catNamesList != null){
        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNamesList);
        setListAdapter(mAdapter);
        getListView().setOnItemLongClickListener(this);
    }else{
        Toast.makeText(getApplicationContext(),
                "The list is Empty, add one please " , Toast.LENGTH_SHORT).show();
    }


        Log.d("myApp", "show string");
        setContentView(R.layout.activity_loglist);
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

//        Toast.makeText(getApplicationContext(),
//                "you selected " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

        NumberList Number = new NumberList();
        //String exploded = l.getItemAtPosition(position).toString().split(".");
        Number.number = l.getItemAtPosition(position).toString();
        //Number.blockTimeType = Integer.parseInt(blockTimeEditText.getText().toString()); //in int
        Number.save();



        Toast.makeText(this, "Saved " + l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        mAdapter.remove(selectedItem);
        mAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(),
                selectedItem + " deleted.",
                Toast.LENGTH_SHORT).show();
        return true;
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
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type1 = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while ( managedCursor.moveToNext() ) {
            String phNumber = managedCursor.getString( number );
            String callType = managedCursor.getString( type1 );
            String callDate = managedCursor.getString( date );
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString( duration );
            String dir = null;
            int dircode = Integer.parseInt( callType );
            switch( dircode ) {
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