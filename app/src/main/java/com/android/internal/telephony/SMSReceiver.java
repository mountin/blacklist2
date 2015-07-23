package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.logging.Filter;

import model.NumberList;

/**
 * Created by mountin on 16.06.2015.
 */
public class SMSReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NumberList Number = new NumberList();
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus.length == 0)
        {
            return;
        }

        Sms sms = Sms.fromPdus(pdus, context);

        String msg = "New SMS Event !!!!! Incomming Number : " + sms._sender;
        Log.d("myApp", msg);
        if (Number.containsBlackListWithNumber(Number.selectListFromDb(), sms._sender)) {
            Log.d("myApp", "The message from" +sms._sender+ "has been blocked Sorry!!!");
            abortBroadcast();
        }else{
            Log.d("myApp", "The number " +sms._sender+ " is NOT in list! :(");
        }

    }
}