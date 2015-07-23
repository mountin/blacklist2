package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.activeandroid.query.Select;

import java.lang.reflect.Method;
import java.util.ArrayList;

import model.NumberList;

/**
 * Created by mountin on 12.06.2015.
 */

public class IncomingCall extends BroadcastReceiver {
    Context context = null;
    private static final String TAG = "Phone call";
    private ITelephony telephonyService;
    static String IncommingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        NumberList Number = new NumberList();
        //make silent
        String IncomedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("onReceive", "  !!!!! Number detected: " + IncomedNumber);

        if (Number.containsBlackListWithNumber(Number.selectListFromDb(), IncomedNumber)) {

            final AudioManager mode = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            Log.v(TAG, "Canceling....");
            //this.deleteLastCallLog(context, IncomedNumber);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            //set normal sound
            mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else {
            Log.d("myApp", "The number " + IncomingCall.IncommingNumber + " is NOT in list! :(");
        }

    }

    public static void deleteLastCallLog(Context context, String phoneNumber) {

        try {
            Log.d("MyPhoneListener", "Start Deleting... !!!");
            //Thread.sleep(4000);
            String strNumberOne[] = {phoneNumber};
            Cursor cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI, null,
                    CallLog.Calls.NUMBER + " = ? ", strNumberOne, CallLog.Calls.DATE + " DESC");

            if (cursor.moveToFirst()) {
                int idOfRowToDelete = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
                int foo = context.getContentResolver().delete(
                        CallLog.Calls.CONTENT_URI,
                        CallLog.Calls._ID + " = ? ",
                        new String[]{String.valueOf(idOfRowToDelete)});
                Log.d("Phone Receive", "number is delited from LOG!!!....");
            }
        } catch (Exception ex) {
            Log.v("deleteNumber",
                    "Exception, unable to remove # from call log: "
                            + ex.toString());
        }
    }


}