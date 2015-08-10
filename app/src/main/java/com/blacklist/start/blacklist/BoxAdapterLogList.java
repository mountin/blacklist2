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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import model.NumberList;
import model.NumberListInterface;

public class BoxAdapterLogList extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<? extends NumberListInterface> objects;

    BoxAdapterLogList(Context context, ArrayList<? extends NumberListInterface> numbers) {
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

        Message p = getProduct(position);


        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date d = new Date();


        SimpleDateFormat dateFormat = null;

        dateFormat = new SimpleDateFormat("HH:mm");

        if ((d.getTime() / 1000) - (p.messageDate.getTime() / 1000) > 24 * 3600) {
            dateFormat = new SimpleDateFormat("dd MMMM", myDateFormatSymbols);
        }
        String date = dateFormat.format(p.messageDate);

        String contactInfo = (p.messageNumber != null)?p.messageNumber:p.number;

        ((TextView) view.findViewById(R.id.tvNumber)).setText(contactInfo);
        ((TextView) view.findViewById(R.id.tvUnix)).setText(date);
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

        //Log.d("asd", "!!!=position=box=" + position);

        return view;
    }

    // обработчик
    AdapterView.OnItemClickListener itemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("asd", "click3!!!=" + position);
        }
    };

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    // get by position
    Message getProduct(int position) {
        return ((Message) getItem(position));
    }


}