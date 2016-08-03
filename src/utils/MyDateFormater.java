package utils;

import models.MyDate;


import java.util.Date;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class MyDateFormater {

    public static MyDate parse(Date date){
        MyDate d = new MyDate();
        d.getCalendar().setTime(date);
        return d;
    }
}
