package com.example.a61dpersonalisedlearningexperienceapp.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIService {
    @Headers({
            "Content-Type: application/json",
           // "Authorization: //Input your own API Key
    @POST("v1/chat/completions")
    Call<JsonObject> getQuiz(@Body JsonObject body);
}
