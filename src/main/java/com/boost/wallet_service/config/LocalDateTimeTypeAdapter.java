package com.boost.wallet_service.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString()); // Serialize as ISO-8601 string
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString());
    }

}
