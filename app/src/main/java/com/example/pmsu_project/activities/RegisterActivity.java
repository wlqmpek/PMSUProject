package com.example.pmsu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.dtos.RegisterBuyerDTO;
import com.example.pmsu_project.dtos.RegisterSellerDTO;
import com.example.pmsu_project.models.Buyer;
import com.example.pmsu_project.models.Seller;
import com.example.pmsu_project.services.BuyerServices;
import com.example.pmsu_project.services.SellerServices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    static final String TAG = RegisterActivity.class.getSimpleName();

    private BuyerServices buyerServices;

    private SellerServices sellerServices;

    private EditText registerFirstNameInput, registerLastNameInput,
            registerUsernameInput, registerPasswordInput,
            registerRepeatedPasswordInput, registerEmailInput, registerNameInput,
            registerAddressInput;

    private int registration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.i(TAG, "Register Activity Started");

        buyerServices = ApiUtils.getBuyerService();
        sellerServices = ApiUtils.getSellerService();

        Spinner registrationSpinner = findViewById(R.id.registrationSpinner);
        ArrayAdapter<CharSequence> spinerAdapter = ArrayAdapter.createFromResource(this, R.array.registrationSpinner, android.R.layout.simple_spinner_item);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registrationSpinner.setAdapter(spinerAdapter);

        registerFirstNameInput = findViewById(R.id.registerFirstNameInput);
        registerLastNameInput = findViewById(R.id.registerLastNameInput);
        registerUsernameInput = findViewById(R.id.registerUsernameInput);
        registerPasswordInput = findViewById(R.id.registerPasswordInput);
        registerRepeatedPasswordInput = findViewById(R.id.registerRepeatedPasswordInput);
        registerEmailInput = findViewById(R.id.registerEmailInput);
        registerAddressInput = findViewById(R.id.registerAddressInput);
        registerNameInput = findViewById(R.id.registerNameInput);

        final Button registerButton = findViewById(R.id.registerButton);

        registrationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    registerEmailInput.setVisibility(View.VISIBLE);
                    registerNameInput.setVisibility(View.VISIBLE);
                    registerButton.setText("Register Seller");
                    registration = 0;
                } else {
                    registerEmailInput.setVisibility(View.GONE);
                    registerNameInput.setVisibility(View.GONE);
                    registerButton.setText("Register Buyer");
                    registration = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areInputsValid()) {

                    register();

                } else {
                    showResponse("Check all input fields.");
                }
            }
        });
    }

    private void register() {
        // Check for the user type.
        if(registration == 0) {
            RegisterSellerDTO registerSellerDTO = new RegisterSellerDTO(
                    registerFirstNameInput.getText().toString(),
                    registerLastNameInput.getText().toString(),
                    registerUsernameInput.getText().toString(),
                    registerPasswordInput.getText().toString(),
                    registerRepeatedPasswordInput.getText().toString(),
                    registerEmailInput.getText().toString(),
                    registerAddressInput.getText().toString(),
                    registerNameInput.getText().toString()
            );

            sellerServices.registerSeller(registerSellerDTO).enqueue(new Callback<Seller>() {
                @Override
                public void onResponse(Call<Seller> call, Response<Seller> response) {
                    if(response.isSuccessful()) {
                        showResponse(String.valueOf(response.code()));
                        Log.i(TAG, "POST submited to API Successfully " + response.code());
                        Log.i(TAG, response.body().toString());
                        Intent i=new Intent(RegisterActivity.this ,LoginActivity.class);
                        startActivity(i);
                    } else {
                        Log.i(TAG, "POST submited to API Sucessffully code = " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Seller> call, Throwable t) {
                    Log.i(TAG, "POST submited to API Failed");
                    showResponse("Check your internet connection. ");
                }
            });
        } else {
            RegisterBuyerDTO registerBuyerDTO = new RegisterBuyerDTO(
                    registerFirstNameInput.getText().toString(),
                    registerLastNameInput.getText().toString(),
                    registerUsernameInput.getText().toString(),
                    registerPasswordInput.getText().toString(),
                    registerRepeatedPasswordInput.getText().toString(),
                    registerAddressInput.getText().toString()
            );

            buyerServices.registerBuyer(registerBuyerDTO).enqueue(new Callback<Buyer>() {
                @Override
                public void onResponse(Call<Buyer> call, Response<Buyer> response) {
                    if(response.isSuccessful()) {
                        showResponse(String.valueOf(response.code()));
                        Log.i(TAG, "POST submited to API Successfully " + response.code());
                        Log.i(TAG, response.body().toString());
                        Intent i=new Intent(RegisterActivity.this ,LoginActivity.class);
                        startActivity(i);
                    } else {
                        Log.i(TAG, "POST submited to API Sucessffully code = " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Buyer> call, Throwable t) {
                    Log.i(TAG, "POST submited to API Failed");
                    showResponse("Check your internet connection.");
                }
            });
        }
    }

    private boolean areInputsValid() {
        boolean valid = true;
        // Validate seller fields if its 0 and buyer if its 1.
        if(registration == 0) {
            if(registerFirstNameInput.getText().toString().isEmpty() ||
            registerLastNameInput.getText().toString().isEmpty() ||
            registerUsernameInput.getText().toString().isEmpty() ||
            registerPasswordInput.getText().toString().isEmpty() ||
            registerRepeatedPasswordInput.getText().toString().isEmpty() ||
            registerEmailInput.getText().toString().isEmpty() ||
            registerAddressInput.getText().toString().isEmpty() ||
            registerNameInput.getText().toString().isEmpty()) {
                valid = false;
            }
        } else {
            if (registerFirstNameInput.getText().toString().isEmpty() ||
                    registerLastNameInput.getText().toString().isEmpty() ||
                    registerUsernameInput.getText().toString().isEmpty() ||
                    registerPasswordInput.getText().toString().isEmpty() ||
                    registerRepeatedPasswordInput.getText().toString().isEmpty() ||
                    registerAddressInput.getText().toString().isEmpty()) {
                valid = false;
            }
        }
        return valid;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}