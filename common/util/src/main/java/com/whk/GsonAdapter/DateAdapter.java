package com.whk.GsonAdapter;

import com.google.gson.*;
import com.whk.DateUtils;

import java.lang.reflect.Type;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return DateUtils.parseDate(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date));
    }
}
