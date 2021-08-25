package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.LoginDTO;
import com.example.pmsu_project.models.JWTResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthServices {
    @POST("auth/login")
    Call<JWTResponse> login(@Body LoginDTO loginDto);
}
