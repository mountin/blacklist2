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
import java.util.Date;
import java.util.TimeZone;

import model.NumberList;
import model.NumberListInterface;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<? extends NumberListInterface> objects;

    BoxAdapter(Context context, ArrayList<? extends NumberListInterface> numbers) {
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

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date d = new Date();

        Log.d("asd", "Number is= "+p.number);

        ((TextView) view.findViewById(R.id.tvNumber)).setText(p.number);
        String setTime = null;

        if (p.unblockedUnixTime != null) {
            Integer hoursLeft = Integer.parseInt(String.valueOf((Long.parseLong(p.unblockedUnixTime) - (d.getTime() / 1000)) / 3600)); //hours left

            if (hoursLeft > 24 && hoursLeft < 720) {

                setTime = hoursLeft / 24 +" "+ ctx.getString(R.string.DaysLeft);
            } else if (hoursLeft >= 720) { //more than 30 days
                setTime = " "+ctx.getString(R.string.Forever);
            } else if (hoursLeft <= 24){
                //Log.d("asd", "Hours 0 Left="+hoursLeft);
                hoursLeft = (hoursLeft < 0) ? 0 : hoursLeft; //set 0 if
                setTime = hoursLeft +" "+ ctx.getString(R.string.HoursLeft);
            }
        }

        if (p.messageDate != null) {
            setTime = p.messageDate;
        }

        ((TextView) view.findViewById(R.id.tvUnix)).setText(setTime);
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);
        Log.d("asd", "!!!=position=box=" + position);

        return view;
    }

    // обработчик
    AdapterView.OnItemClickListener itemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("asd", "click3!!!=" + position);
        }
    };

    // get by position
    NumberList getProduct(int position) {
        return ((NumberList) getItem(position));
    }


}