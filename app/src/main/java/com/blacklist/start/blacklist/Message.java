package com.blacklist.start.blacklist;

import java.util.Date;

import model.NumberList;
import model.NumberListInterface;

/**
 * Created by mountin on 22.06.2015.
 */

public class Message implements NumberListInterface {

    public String messageNumber, messageContent;
    public Date messageDate;
    String number;
    public String toString() {
        return this.messageNumber;// + ". [" + this.messageContent + "]";//. unix[" + this.unblockedUnixTime + "]";
    }
}