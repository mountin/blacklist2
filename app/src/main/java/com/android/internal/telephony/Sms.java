package com.android.internal.telephony;

import android.content.Context;
import android.telephony.SmsMessage;

/**
 * Created by mountin on 16.06.2015.
 */
public class Sms {
    private String _body;
    public String _sender;
    private long _timestamp;
    public static Sms fromPdus(Object[] pdus, Context context)
    {
        Sms result = new Sms();
        for (int i = 0; i < pdus.length; i++)
        {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
            result._body += sms.getMessageBody();
        }

        SmsMessage first = SmsMessage.createFromPdu((byte[]) pdus[0]);
        result._sender = first.getOriginatingAddress().toString();
        result._timestamp = first.getTimestampMillis();

        return result;
    }

}
