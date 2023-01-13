package com.example.skincapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.skincapp.ui.login.LoginActivity;

public class WelcomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Button button = findViewById(R.id.register);// You have to import class manualy the system doesnt doit for you
        button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePageActivity.this,SignUpActivity2.class);
                startActivity(intent);
           }
        });

        Button button2 = findViewById(R.id.signin);// You have to import class manualy the system doesnt doit for you
        button2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
                 startActivity(intent);
                    }
        });

        Button button3 = findViewById(R.id.about_us);// You have to import class manualy the system doesnt doit for you
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePageActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.help);// You have to import class manualy the system doesnt doit for you
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomePageActivity.this, Help.class);
                startActivity(intent);
            }
        });
                }



    }
