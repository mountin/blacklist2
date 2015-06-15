package com.blacklist.start.blacklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;

/**
 * Created by mountin on 11.06.2015.
 */
public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdus.length];
            ArrayList<String> numbers = new ArrayList<String>();
            ArrayList<String> messages = new ArrayList<String>();
            for (int i=0; i<msgs.length; i++){ //пробегаемся по всем полученным сообщениям
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                numbers.add(msgs[i].getOriginatingAddress()); //получаем номер отправителя
                messages.add(msgs[i].getMessageBody().toString());//получаем текст сообщения
            }
            if (messages.size() > 0){
                //делаем что-то с сообщениями
            }
        }
    }



}