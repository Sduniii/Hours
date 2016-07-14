package data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class MyDate extends Date {

    public MyDate(String str){
        super(str);
    }

    public MyDate(){
        super();
    }
    @Override
    public String toString(){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z").format(this);
    }
}
