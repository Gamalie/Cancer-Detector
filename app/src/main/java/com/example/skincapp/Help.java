package com.example.skincapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Button button5 = findViewById(R.id.help);// You have to import class manualy the system doesnt doit for you
        //button5.setOnClickListener(view -> {
            //Intent intent = new Intent(Help.this, Assistance.class);
            //startActivity(intent);
       // });
    }

    }
