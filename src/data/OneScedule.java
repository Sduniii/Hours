package data;

import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tbaios on 13.07.2016.
 */
public class OneScedule {


    @Expose
    private Date start;
    @Expose
    private Date stop;


    public OneScedule(Date start, Date stop){

        this.start = start;
        this.stop = stop;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "OneScedule{" +
                "start=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").format(start) +
                ", stop=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").format(stop) +
                '}';
    }
}
