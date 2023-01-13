package com.example.skincapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        Button button5 = findViewById(R.id.register_page);// You have to import class manualy the system doesnt doit for you
        button5.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity2.this, HomePage.class);
            startActivity(intent);
        });
    }
}