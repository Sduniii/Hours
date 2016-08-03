package models;

import com.google.gson.*;
import utils.MyDateFormater;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class OneSceduleDeserializer implements JsonDeserializer<OneScedule> {


    @Override
    public OneScedule deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            final JsonElement jsonStart = jsonObject.get("start");
            final MyDate start = MyDateFormater.parse((new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z")).parse(jsonStart.getAsString()));
            final JsonElement jsonStop = jsonObject.get("stop");
            final MyDate stop = MyDateFormater.parse((new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z")).parse(jsonStop.getAsString()));

            return new OneScedule(start,stop);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
