package com.blacklist.start.blacklist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
import model.NumberList;


public class StopListActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

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

        this.catNamesList = this.selectListFromDb();
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
        setContentView(R.layout.activity_stoplist);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getApplicationContext(),
                "you selected " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
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
}