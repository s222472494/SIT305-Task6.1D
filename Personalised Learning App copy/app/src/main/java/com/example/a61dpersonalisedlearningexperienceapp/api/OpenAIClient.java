package com.example.a61dpersonalisedlearningexperienceapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenAIClient {

    private static final String BASE_URL = "https://api.openai.com/";
    private static Retrofit retrofit = null;

    public static OpenAIService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(OpenAIService.class);
    }
}
