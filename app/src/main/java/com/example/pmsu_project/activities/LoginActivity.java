package com.example.pmsu_project.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.dtos.LoginDTO;
import com.example.pmsu_project.models.JWTResponse;
import com.example.pmsu_project.retrofit.RetrofitClient;
import com.example.pmsu_project.services.AuthServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    static final String TAG = LoginActivity.class.getSimpleName();
    private AuthServices authServices;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText loginUsernameInput = findViewById(R.id.loginUsernameInput);
        final EditText loginPasswordInput = findViewById(R.id.loginPasswordInput);
        final Button loginButton = findViewById(R.id.loginButton);
        final Button registrationButton = findViewById(R.id.goToRegisterActivityButton);

        authServices = ApiUtils.getAuthService();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDTO loginDTO = new LoginDTO(
                        loginUsernameInput.getText().toString().trim(),
                        loginPasswordInput.getText().toString().trim());
                sendPost(loginDTO);
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void sendPost(LoginDTO loginDTO) {
        authServices.login(loginDTO).enqueue(new Callback<JWTResponse>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                if(response.isSuccessful()) {
                    showResponse(String.valueOf(response.code()));
//                    showResponse(response.body().getJwt());
                    Log.i(TAG, "POST submited to API Sucessfuly " + response.body());
                    RetrofitClient.token = response.body().getJwt();
                    saveToSharedPreference(response.body().getJwt());
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Log.i(TAG, "POST submited to API Sucessfuly code = " + response.code());
                    showResponse("Invalid Cretentials, try again. " + response.code());
                }

            }

            @Override
            public void onFailure(Call<JWTResponse> call, Throwable t) {
                Log.i(TAG, "POST submited to API Sucessfuly code = " + t);
                showResponse("Check your internet connection.");
            }
        } );
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }

    public void saveToSharedPreference(String token) {
        sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
        Toast.makeText(LoginActivity.this, "Saved " + getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE).getString("token", ""), Toast.LENGTH_LONG).show();

    }
}