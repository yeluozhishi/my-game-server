package com.whk.GsonAdapter;

import com.google.gson.*;
import com.whk.TimeUtils;

import java.lang.reflect.Type;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return TimeUtils.parseDate(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(TimeUtils.parseDateToStr(TimeUtils.YYYY_MM_DD_HH_MM_SS, date));
    }
}
