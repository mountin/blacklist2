package com.blacklist.start.blacklist;

/**
 * Created by mountin on 24.06.2015.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.NumberList;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<NumberList> objects;

    BoxAdapter(Context context, ArrayList<NumberList> numbers) {
        ctx = context;
        objects = numbers;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        NumberList p = getProduct(position);


        ((TextView) view.findViewById(R.id.tvNumber)).setText(p.number);
        ((TextView) view.findViewById(R.id.tvUnix)).setText(p.unblockedUnixTime + "");
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

        Log.d("asd", "!!!=position=box=" + position);

        return view;
    }

    // обработчик
    AdapterView.OnItemClickListener itemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("asd", "click3!!!="+position);
        }
    };

    // get by position
    NumberList getProduct(int position) {
        return ((NumberList) getItem(position));
    }


}