package model;

import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by mountin on 10.06.2015.
 */
@Table(name="NumberLists")
public class NumberList extends Model implements Comparator{
    public NumberList()
    {
        super();
    }

    @Column(name = "number")
    public String number;
    @Column(name = "blockTimeType")
    public int blockTimeType;
    @Column(name = "unblockedUnixTime")
    public String unblockedUnixTime;
    @Column(name = "dateStart")
    public String dateStart;
    @Column(name = "status")
    public int status;
    @Column(name = "isActive")
    public int isActive;




    public String toString() {
        return this.number + ". [" + this.status + "] unix[" + this.unblockedUnixTime + "]";
    }

    @Override
    public int compare(Object lhs, Object rhs) {

        NumberList myobj = (NumberList) lhs;

        if(myobj.unblockedUnixTime != null)
            return 0;

        Date date = (Date) rhs;
        long dateNow =  (date.getTime() / 1000);


        Log.d("asd", "!!! check obj="+myobj.unblockedUnixTime+" with"+dateNow);


        //Lets find any OLD object
        if(Long.parseLong(myobj.unblockedUnixTime) <= dateNow) {

            Log.d("asd", "The olde Number FOUNDED!!!!="+myobj.number);

            return 1;
        }

        return 0;
    }
}


