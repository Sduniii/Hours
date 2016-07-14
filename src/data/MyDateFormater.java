package data;

import java.util.Date;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class MyDateFormater {

    public static MyDate parse(Date date){
        MyDate d = new MyDate();
        d.setTime(date.getTime());
        return d;
    }
}
