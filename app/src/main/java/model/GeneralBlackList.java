package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mountin on 23.07.2015.
 */
@Table(name="GeneralBlackList")
public class GeneralBlackList {
    public GeneralBlackList()
    {
        super();
    }
    @Column(name = "number")
    public String number;
    @Column(name = "CountryCode")
    public String CountryCode;
    @Column(name = "unblockedUnixTime")
    public String unblockedUnixTime;
    @Column(name = "dateStart")
    public String dateStart;
    @Column(name = "status")
    public int status;
    @Column(name = "reason")
    public String reason;
    @Column(name = "userId")
    public int userId;
}
