package com.example.pmsu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.pmsu_project.R;

public class MainActivity extends AppCompatActivity {
    static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIsUserLoggedIn();

    }

    public void checkIsUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("token")) {

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(MainActivity.this ,LoginActivity.class);
                    startActivity(i);
                }
            }, 500);
        }
    }
}