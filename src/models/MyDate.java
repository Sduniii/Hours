package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class MyDate{

    private Calendar calendar;

    public MyDate(){
        calendar = new GregorianCalendar();
    }

    public MyDate(Calendar calendar) {
        this.calendar = calendar;
    }


    @Override
    public String toString(){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").format(calendar.getTime());
    }
    public Calendar getCalendar() {
        return calendar;
    }
}
