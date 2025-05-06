package com.example.a61dpersonalisedlearningexperienceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61dpersonalisedlearningexperienceapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {

    private ArrayList<String> selectedInterests = new ArrayList<>();
    private final int MAX_SELECTION = 5;
    private String username;  // 👈 Add this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        username = getIntent().getStringExtra("USERNAME");  // 👈 Get the username from intent

        int[] buttonIds = {
                R.id.buttonAlgo, R.id.buttonDS, R.id.buttonWebDev, R.id.buttonTesting,
                R.id.buttonAI, R.id.buttonMobile, R.id.buttonSecurity, R.id.buttonUX
        };

        for (int id : buttonIds) {
            MaterialButton button = findViewById(id);
            button.setOnClickListener(v -> {
                String interest = button.getText().toString();
                if (selectedInterests.contains(interest)) {
                    selectedInterests.remove(interest);
                    button.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
                } else {
                    if (selectedInterests.size() >= MAX_SELECTION) {
                        Toast.makeText(this, "Select up to 5 interests only", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    selectedInterests.add(interest);
                    button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_light));
                }
            });
        }

        findViewById(R.id.buttonNext).setOnClickListener(v -> {
            if (selectedInterests.isEmpty()) {
                Toast.makeText(this, "Please select at least 1 interest", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(InterestsActivity.this, HomeActivity.class);
                intent.putStringArrayListExtra("INTERESTS", selectedInterests);
                intent.putExtra("USERNAME", username);  // 👈 Pass username to HomeActivity
                startActivity(intent);
                finish();
            }
        });
    }
}
