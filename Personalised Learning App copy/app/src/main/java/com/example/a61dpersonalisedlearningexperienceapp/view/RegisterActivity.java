package com.example.a61dpersonalisedlearningexperienceapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a61dpersonalisedlearningexperienceapp.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, confirmEmail, password, confirmPassword, phone;
    private Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        confirmEmail = findViewById(R.id.editTextConfirmEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
        phone = findViewById(R.id.editTextPhone);
        createAccountBtn = findViewById(R.id.buttonCreateAccount);

        createAccountBtn.setOnClickListener(view -> {
            if (validateInputs()) {
                // Proceed to interests selection screen
                Intent intent = new Intent(RegisterActivity.this, InterestsActivity.class);
                intent.putExtra("USERNAME", username.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(username.getText()) ||
                TextUtils.isEmpty(email.getText()) ||
                TextUtils.isEmpty(confirmEmail.getText()) ||
                TextUtils.isEmpty(password.getText()) ||
                TextUtils.isEmpty(confirmPassword.getText()) ||
                TextUtils.isEmpty(phone.getText())) {

            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.getText().toString().equals(confirmEmail.getText().toString())) {
            Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
