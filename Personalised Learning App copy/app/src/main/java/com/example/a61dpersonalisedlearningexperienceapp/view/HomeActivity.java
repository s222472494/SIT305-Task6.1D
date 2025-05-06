package com.example.a61dpersonalisedlearningexperienceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61dpersonalisedlearningexperienceapp.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewTaskLabel;
    private LinearLayout layoutInterests, layoutTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutInterests = findViewById(R.id.layoutInterests);
        layoutTask = findViewById(R.id.layoutTask);
        textViewTaskLabel = findViewById(R.id.textViewTaskLabel);


        ArrayList<String> interests = getIntent().getStringArrayListExtra("INTERESTS");

        TextView welcomeText = findViewById(R.id.textViewWelcome);
        String username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.isEmpty()) {
            welcomeText.setText("Hello, " + username + "!");
        }

        if (interests != null && !interests.isEmpty()) {
            for (String interest : interests) {
                TextView tag = new TextView(this);
                tag.setText(interest);
                tag.setPadding(16, 8, 16, 8);
                tag.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                tag.setTextColor(getResources().getColor(android.R.color.black));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(8, 8, 8, 8);
                tag.setLayoutParams(params);

                layoutInterests.addView(tag);
            }
        }

        layoutTask.setOnClickListener(v -> {
            // Go to the "Generated Task" screen
            Intent intent = new Intent(HomeActivity.this, GeneratedTaskActivity.class);
            intent.putStringArrayListExtra("INTERESTS", interests);
            startActivity(intent);
        });
    }
}
