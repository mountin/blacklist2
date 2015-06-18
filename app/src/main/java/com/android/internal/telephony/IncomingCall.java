package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.activeandroid.query.Select;
import com.blacklist.start.blacklist.CallLogUtility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.NumberList;

/**
 * Created by mountin on 12.06.2015.
 */

public class IncomingCall extends BroadcastReceiver {
    Context context = null;
    private static final String TAG = "Phone call";
    private ITelephony telephonyService;
    static String IncommingNumber;

    public boolean containsBlackListWithNumber(ArrayList<NumberList> paragems, String CompareNumber) {

        for (NumberList p : paragems) {
            if (p.number.equals(CompareNumber)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList selectListFromDb() {
        ArrayList NumberList = new Select().from(model.NumberList.class).execute();

        if (NumberList.size() != 0) {
            return NumberList;
        } else
            return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String IncomedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("onReceive", "  !!!!! Number detected: " + IncomedNumber);


        try {

            if (this.containsBlackListWithNumber(this.selectListFromDb(), IncomedNumber)) {

                Log.v(TAG, "Canceling....");


                TelephonyManager telephony = (TelephonyManager)
                        context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Class c = Class.forName(telephony.getClass().getName());
                    Method m = c.getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(telephony);
                    //telephonyService.silenceRinger();
                    telephonyService.endCall();
                    Log.d("MyPhoneListener", "Call canceled !!!!!");

//                    Log.d("MyPhoneListener", "Deleting  number from LOG!!!!!...");
//                    ContentResolver cr = context.getContentResolver();
//                    CallLogUtility utility = new CallLogUtility();
//                    utility.DeleteNumFromCallLog(cr, IncomedNumber);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                Log.d("myApp", "The number " + IncomingCall.IncommingNumber + " is NOT in list! :(");
            }
        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }


}