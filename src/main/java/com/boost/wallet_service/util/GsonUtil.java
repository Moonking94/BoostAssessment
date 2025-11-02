package com.boost.wallet_service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maybank.assessment.config.LocalDateTimeTypeAdapter;

import java.time.LocalDateTime;

public class GsonUtil {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public static Gson getGson() {
        return gson;
    }
}
