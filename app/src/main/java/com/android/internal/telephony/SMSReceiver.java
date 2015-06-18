package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
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
    public ArrayList selectListFromDb() {
        ArrayList NumberList = new Select().from(model.NumberList.class).execute();

        if (NumberList.size() != 0) {
            return NumberList;
        } else
            return null;
    }

    public boolean containsBlackListWithNumber(ArrayList<NumberList> paragems, String CompareNumber) {

        for (NumberList p : paragems) {
            if (p.number.equals(CompareNumber)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus.length == 0)
        {
            return;
        }

        Sms sms = Sms.fromPdus(pdus, context);

        String msg = "New SMS Event !!!!! Incomming Number : " + sms._sender;
        Log.d("myApp", msg);
        if (this.containsBlackListWithNumber(this.selectListFromDb(), sms._sender)) {
            Log.d("myApp", "The message from" +sms._sender+ "has been blocked Sorry!!!");
            abortBroadcast();
        }else{
            Log.d("myApp", "The number " +sms._sender+ " is NOT in list! :(");
        }

    }
}