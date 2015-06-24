package model;

import android.text.Editable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mountin on 10.06.2015.
 */
@Table(name="NumberLists")
public class NumberList extends Model {
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




    public String toString() {
        return this.number + ". [" + this.status + "] unix[" + this.unblockedUnixTime + "]";
    }
}


