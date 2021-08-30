package com.example.pmsu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.pmsu_project.R;
import com.example.pmsu_project.models.LoggedUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkWhichUserIsLoggedIn();
    }

    public void checkWhichUserIsLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("token")) {
            LoggedUser.login(sharedPreferences.getString("token", ""));

            // Seller is logged in
            if(LoggedUser.getRole().getName().equals("ROLE_SELLER")) {
                showResponse("Ulogovan je prodavac.");
                // Buyer is loggedIn
            } else if(LoggedUser.getRole().getName().equals("ROLE_BUYER")) {
                showResponse("Ulogovan je kupac.");
                Intent i=new Intent(MainActivity.this, ListSellersActivity.class);
                startActivity(i);
            } else {
                showResponse("Ulogovan je admin.");
                // Admin is loggedIn
            }
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

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}