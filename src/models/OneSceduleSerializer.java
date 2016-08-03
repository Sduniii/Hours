package models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Tbaios on 14.07.2016.
 */
public class OneSceduleSerializer implements JsonSerializer<OneScedule> {

    @Override
    public JsonElement serialize(final OneScedule scedule, Type type, JsonSerializationContext jsonSerializationContext) {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("start", scedule.getStart().toString());
        jsonObject.addProperty("stop", scedule.getStop().toString());

        return jsonObject;
    }
}
