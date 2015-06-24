package com.blacklist.start.blacklist;

/**
 * Created by mountin on 22.06.2015.
 */

public class Message {
    public String messageNumber, messageContent,messageDate;
    public String toString() {
        return this.messageNumber;// + ". [" + this.messageContent + "]";//. unix[" + this.unblockedUnixTime + "]";
    }
}