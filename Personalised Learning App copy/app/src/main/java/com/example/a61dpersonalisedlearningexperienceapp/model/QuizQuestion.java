package com.example.a61dpersonalisedlearningexperienceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestion implements Parcelable {
    public String question;
    public List<String> options;
    public String answer;

    public QuizQuestion(String question, List<String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    protected QuizQuestion(Parcel in) {
        question = in.readString();
        options = in.createStringArrayList();
        answer = in.readString();
    }

    public static final Creator<QuizQuestion> CREATOR = new Creator<QuizQuestion>() {
        @Override
        public QuizQuestion createFromParcel(Parcel in) {
            return new QuizQuestion(in);
        }

        @Override
        public QuizQuestion[] newArray(int size) {
            return new QuizQuestion[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(options);
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

