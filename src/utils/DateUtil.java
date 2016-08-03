package utils;

import models.MyDate;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Tbaios on 03.08.2016.
 */
public class DateUtil {

    public static MyDate roundToQuarterUp(MyDate date){
        Calendar cal = date.getCalendar();
        int minute = cal.get(Calendar.MINUTE);
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double calc = ((double)minute)/15.0;
        minute = Integer.parseInt(df.format(calc))*15;
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND, 0);
        //System.out.println(cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)+ ":" + cal.get(Calendar.SECOND));
        return new MyDate(cal);
    }

    public static MyDate roundToQuarterDown(MyDate date){
        Calendar cal = date.getCalendar();
        int minute = cal.get(Calendar.MINUTE);
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.DOWN);
        double calc = ((double)minute)/15.0;
        minute = Integer.parseInt(df.format(calc))*15;
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND, 0);
        //System.out.println(cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)+ ":" + cal.get(Calendar.SECOND));
        return new MyDate(cal);
    }
}
