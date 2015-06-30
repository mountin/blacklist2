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

    static int currentPosition;

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
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        NumberList p = getProduct(position);
        BoxAdapter.currentPosition = position;

        // и картинка
        ((TextView) view.findViewById(R.id.tvNumber)).setText(p.number);
        ((TextView) view.findViewById(R.id.tvUnix)).setText(p.unblockedUnixTime + "");
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

        //ListView asd = (ListView) view.findViewById(R.id.list_item);

        //asd.setOnItemClickListener(itemclick);

        //bookListView = (ListView) findViewById(R.id.android.list);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        Log.d("asd", "!!!=position=" + position);

        return view;
    }


    // обработчик
    AdapterView.OnItemClickListener itemclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("asd", "click3!!!="+position);
        }
    };

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            Log.d("asd", "click!!! posit="+buttonView.getTag());

            // меняем данные товара (в корзине или нет)
            //getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };

    // товар по позиции
    NumberList getProduct(int position) {
        return ((NumberList) getItem(position));
    }


}