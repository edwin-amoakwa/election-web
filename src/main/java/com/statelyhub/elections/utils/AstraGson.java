/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Edwin
 */
public class AstraGson 
{
    
    public static String toJson(Object object){
        return AstraGson.getGson().toJson(object);
    }
    
        public static Gson getGson()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
        return gson;
    }
    
    
    static class LocalDateSerializer implements JsonSerializer<LocalDate>
    {
        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context)
        {
            if(date == null)
            {
                return null;
            }
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }
    }

    static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime>
    {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context)
        {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    static class LocalTimeSerializer implements JsonSerializer<LocalTime>
    {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;
        @Override
        public JsonElement serialize(LocalTime localDateTime, Type srcType, JsonSerializationContext context)
        {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    static class LocalDateDeserializer implements JsonDeserializer<LocalDate>
    {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException
        {
            return LocalDate.parse(json.getAsString(),
                    DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.ENGLISH));
        }
    }

    static class LocalDateTimeDeserializer implements JsonDeserializer< LocalDateTime>
    {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException
        {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(Locale.ENGLISH));
        }
    }

    static class LocalTimeDeserializer implements JsonDeserializer<LocalTime>
    {
        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException
        {
            return LocalTime.parse(json.getAsString(),
                    DateTimeFormatter.ISO_TIME.withLocale(Locale.ENGLISH));
        }
    }
    
    
    
    public static class LocalDateAdapter extends TypeAdapter<LocalDate> {
    @Override
    public void write( final JsonWriter jsonWriter, final LocalDate localDate ) throws IOException {
        jsonWriter.value(localDate.toString());
    }

    @Override
    public LocalDate read( final JsonReader jsonReader ) throws IOException {
        return LocalDate.parse(jsonReader.nextString());
    }
}

    
}
