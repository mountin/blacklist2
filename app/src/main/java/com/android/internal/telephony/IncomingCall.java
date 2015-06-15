package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.activeandroid.query.Select;

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

        Log.d("myApp", "someone is calling 595 !!!!!!!!!!");
        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            Log.d("MyPhoneListener", "  !!!!! incoming FROM onReceive:" + IncomingCall.IncommingNumber);

            if (this.containsBlackListWithNumber(this.selectListFromDb(), IncomingCall.IncommingNumber)) {

                Log.v(TAG, "Receving....");
                TelephonyManager telephony = (TelephonyManager)
                        context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Class c = Class.forName(telephony.getClass().getName());
                    Method m = c.getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(telephony);
                    //telephonyService.silenceRinger();
                    telephonyService.endCall();
                    //Log.v(TAG, "Call canceled");
                    Log.d("MyPhoneListener", "Call canceled !!!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {


            IncomingCall.IncommingNumber = incomingNumber;

            if (state == 1) {

                String msg = "New Phone Call Event !!!!! Incomming Number : " + incomingNumber;
                Log.d("myApp", msg);

            }
        }
    }
}