package com.example.a61dpersonalisedlearningexperienceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61dpersonalisedlearningexperienceapp.R;
import com.example.a61dpersonalisedlearningexperienceapp.model.QuizQuestion;
import com.example.a61dpersonalisedlearningexperienceapp.utils.AIQuizGenerator;

import java.util.ArrayList;
import java.util.List;

public class GeneratedTaskActivity extends AppCompatActivity {

    private RadioGroup radioGroupQ1, radioGroupQ2;
    private Button submitButton;
    private TextView question1, question2;
    private ProgressBar progressBar;

    private List<QuizQuestion> quizQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_task);

        question1 = findViewById(R.id.textQuestion1);
        question2 = findViewById(R.id.textQuestion2);
        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        submitButton = findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBar);

        submitButton.setEnabled(false); // Disable until AI loads

        ArrayList<String> interests = getIntent().getStringArrayListExtra("INTERESTS");
        if (interests == null || interests.isEmpty()) {
            interests = new ArrayList<>();
            interests.add("AI"); // Default fallback
        }

        // Show loading
        progressBar.setVisibility(View.VISIBLE);

        // Call OpenAI to generate questions
        AIQuizGenerator.generateFromAI(interests, new AIQuizGenerator.AIResponseListener() {
            @Override
            public void onSuccess(List<QuizQuestion> result) {
                runOnUiThread(() -> {
                    try {
                        quizQuestions = result;

                        if (quizQuestions.size() >= 2) {
                            QuizQuestion q1 = quizQuestions.get(0);
                            QuizQuestion q2 = quizQuestions.get(1);

                            question1.setText("1. " + q1.question);
                            question2.setText("2. " + q2.question);

                            for (int i = 0; i < 3; i++) {
                                ((RadioButton) radioGroupQ1.getChildAt(i)).setText(q1.options.get(i));
                                ((RadioButton) radioGroupQ2.getChildAt(i)).setText(q2.options.get(i));
                            }
                        }

                        submitButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        question1.setText("⚠️ Error loading quiz");
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    question1.setText("⚠️ Failed to load questions\n" + error);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });

        submitButton.setOnClickListener(v -> {
            int selectedQ1 = radioGroupQ1.getCheckedRadioButtonId();
            int selectedQ2 = radioGroupQ2.getCheckedRadioButtonId();

            if (selectedQ1 == -1 || selectedQ2 == -1) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            } else {
                // Get user's selected answers
                String answer1 = ((RadioButton) findViewById(selectedQ1)).getText().toString();
                String answer2 = ((RadioButton) findViewById(selectedQ2)).getText().toString();

                // Pass to TaskResultActivity
                Intent intent = new Intent(GeneratedTaskActivity.this, TaskResultActivity.class);
                intent.putExtra("ANS1", answer1);
                intent.putExtra("ANS2", answer2);
                intent.putParcelableArrayListExtra("QUIZ_LIST", new ArrayList<>(quizQuestions)); // ✅ Add this line
                startActivity(intent);
                finish();
            }
        });
    }
}
