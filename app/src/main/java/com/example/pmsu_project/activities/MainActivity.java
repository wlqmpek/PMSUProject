package com.example.pmsu_project.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.buyers.ListSellersActivity;
import com.example.pmsu_project.activities.sellers.ListSellersArticles2Activity;
import com.example.pmsu_project.models.LoggedUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    static final String TAG = LoginActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isStoragePermissionGranted()) {
            checkWhichUserIsLoggedIn();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkWhichUserIsLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("token")) {
            LoggedUser.login(sharedPreferences.getString("token", ""));

            // Seller is logged in
            if(LoggedUser.getRole().getName().equals("ROLE_SELLER")) {
                showResponse("Ulogovan je prodavac.");
                Intent i=new Intent(MainActivity.this, ListSellersArticles2Activity.class);
                startActivity(i);
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
    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                //all permissions have been granted
                checkWhichUserIsLoggedIn(); //call your dependent logic
            }
        }
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}