package data;

import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tbaios on 13.07.2016.
 */
public class OneScedule {


    @Expose
    private MyDate start;
    @Expose
    private MyDate stop;

    private long duration;


    public OneScedule(MyDate start, MyDate stop){
        this.start = start;
        this.stop = stop;
        calcDuration(TimeScale.MINS);
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(MyDate stop) {
        this.stop = stop;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(MyDate start) {
        this.start = start;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "OneScedule{" +
                "start=" + start +
                ", stop=" + stop +
                '}';
    }

    public long calcDuration(TimeScale scale){

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(start);
        calendar2.setTime(stop);
        long milsecs1= calendar1.getTimeInMillis();
        long milsecs2 = calendar2.getTimeInMillis();
        long diff = milsecs2 - milsecs1;
        switch (scale){
            case MILLIS:
                return diff;
            case SECS:
                return diff / 1000;
            case MINS:
                return diff / (60 * 1000);
            case HOURS:
                return diff / (60 * 60 * 1000);
            case DAYS:
                return diff / (24 * 60 * 60 * 1000);
            default:
                return 0;
        }
    }

    public enum TimeScale{
        MILLIS,SECS,MINS,HOURS,DAYS
    }
}
