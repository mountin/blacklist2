package model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

/**
 * Created by mountin on 09.06.2015.
 */
@Table(name="Users")
public class User extends Model {
    public String name;
    public int age;
}

