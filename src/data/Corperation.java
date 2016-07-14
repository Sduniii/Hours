package data;

import com.google.gson.annotations.Expose;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tbaios on 13.07.2016.
 */
public class Corperation {

    @Expose
    private List<OneScedule> times;
    @Expose
    private String name;

    public Corperation(String name) {
        this.name = name;
        this.times = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<OneScedule> getTimes() {
        return times;
    }

    public void setTimes(List<OneScedule> times) {
        this.times = times;
    }

    @Override
    public String toString() {
        String d = "Times at " + this.name + ": ";
        for (OneScedule o : this.times) {
            d = d + "{ " + o.getStart() + " - " + o.getStop() + "}";
        }

        return d;
    }
}
