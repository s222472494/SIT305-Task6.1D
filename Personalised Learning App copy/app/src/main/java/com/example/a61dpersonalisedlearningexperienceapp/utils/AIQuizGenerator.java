package com.example.a61dpersonalisedlearningexperienceapp.utils;

import android.util.Log;

import com.example.a61dpersonalisedlearningexperienceapp.api.OpenAIClient;
import com.example.a61dpersonalisedlearningexperienceapp.api.OpenAIService;
import com.example.a61dpersonalisedlearningexperienceapp.model.QuizQuestion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AIQuizGenerator {

    public static void generateFromAI(List<String> interests, AIResponseListener listener) {
        OpenAIService api = OpenAIClient.getService();

        String prompt = "Generate 2 multiple-choice quiz questions (with 3 options each) based on the following student interests: " +
                String.join(", ", interests) +
                ". Return them in JSON format like: [{question: '...', options: ['...'], correct: '...'}]";

        JsonObject messageObj = new JsonObject();
        messageObj.addProperty("role", "user");
        messageObj.addProperty("content", prompt);

        JsonArray messagesArray = new JsonArray();
        messagesArray.add(messageObj);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        requestBody.add("messages", messagesArray);

        Call<JsonObject> call = api.getQuiz(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JsonObject responseBody = response.body();
                        JsonArray choices = responseBody.getAsJsonArray("choices");
                        if (choices != null && choices.size() > 0) {
                            JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                            String content = message.get("content").getAsString();

                            // Parse the JSON response
                            JsonParser parser = new JsonParser();
                            JsonElement element = parser.parse(content);
                            JsonArray quizArray = element.getAsJsonArray();

                            List<QuizQuestion> quizQuestions = new ArrayList<>();

                            for (JsonElement elem : quizArray) {
                                JsonObject obj = elem.getAsJsonObject();
                                String question = obj.get("question").getAsString();
                                JsonArray optionsJson = obj.getAsJsonArray("options");
                                List<String> options = new ArrayList<>();
                                for (JsonElement optionElem : optionsJson) {
                                    options.add(optionElem.getAsString());
                                }
                                String correct = obj.get("correct").getAsString();
                                quizQuestions.add(new QuizQuestion(question, options, correct));
                            }

                            listener.onSuccess(quizQuestions);
                        } else {
                            listener.onFailure("No choices in response.");
                        }
                    } catch (Exception e) {
                        listener.onFailure("Parsing error: " + e.getMessage());
                    }
                } else {
                    try {
                        Log.e("OPENAI_ERROR", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure("Failed to get quiz: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public interface AIResponseListener {
        void onSuccess(List<QuizQuestion> result);
        void onFailure(String error);
    }
}
