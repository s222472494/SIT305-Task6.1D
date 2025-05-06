package com.example.a61dpersonalisedlearningexperienceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61dpersonalisedlearningexperienceapp.R;
import com.example.a61dpersonalisedlearningexperienceapp.model.QuizQuestion;

import java.util.ArrayList;

public class TaskResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    private ArrayList<QuizQuestion> quizList;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_result);

        resultTextView = findViewById(R.id.textViewResultSummary);
        continueButton = findViewById(R.id.buttonContinue);

        // Get data from intent
        String ans1 = getIntent().getStringExtra("ANS1");
        String ans2 = getIntent().getStringExtra("ANS2");
        quizList = getIntent().getParcelableArrayListExtra("QUIZ_LIST");

        if (quizList != null && quizList.size() >= 2) {
            int score = 0;

            if (ans1.equalsIgnoreCase(quizList.get(0).answer)) score++;
            if (ans2.equalsIgnoreCase(quizList.get(1).answer)) score++;

            String feedback = "You got " + score + " out of 2 correct.\n\n";
            feedback += "Q1: " + quizList.get(0).question + "\nYour answer: " + ans1 +
                    "\nCorrect: " + quizList.get(0).answer + "\n\n";

            feedback += "Q2: " + quizList.get(1).question + "\nYour answer: " + ans2 +
                    "\nCorrect: " + quizList.get(1).answer;

            resultTextView.setText(feedback);
        } else {
            resultTextView.setText("Error: Quiz data not found.");
        }

        // Navigate to HomeActivity on click
        continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskResultActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Prevent going back to result screen
        });

    }
}
