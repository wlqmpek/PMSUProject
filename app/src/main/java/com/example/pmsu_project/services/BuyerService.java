package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.RegisterBuyerDTO;
import com.example.pmsu_project.models.Buyer;
import com.example.pmsu_project.models.JWTResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BuyerService {
    @POST("buyers/registration")
    Call<Buyer> registerBuyer(@Body RegisterBuyerDTO registerBuyerDTO);
}
