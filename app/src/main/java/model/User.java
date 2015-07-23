package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by mountin on 09.06.2015.
 */
@Table(name="User")
public class User extends Model {
    @Column(name = "userIdentificator")
    public int userIdentificator;
    @Column(name = "name")
    public String name;
    @Column(name = "age")
    public int age;
    @Column(name = "passwd")
    public int passwd;
    @Column(name = "country")
    public int country;
    @Column(name = "status")
    public int status;
}

