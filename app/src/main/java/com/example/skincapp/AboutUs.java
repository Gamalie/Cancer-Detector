package com.example.skincapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.skincapp.ui.main.AboutUsFragment;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


           // Button button3 = findViewById(R.id.about_us);// You have to import class manualy the system doesnt doit for you
            //button3.setOnClickListener(this::onClick);

        }

    //private void onClick(View view) {
        //Intent intent = new Intent(AboutUs.this, Information.class);
        //startActivity(intent);
    }
