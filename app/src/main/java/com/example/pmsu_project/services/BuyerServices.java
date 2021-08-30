package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.RegisterBuyerDTO;
import com.example.pmsu_project.models.Buyer;
import com.example.pmsu_project.models.JWTResponse;
import com.example.pmsu_project.models.Seller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BuyerServices {
    @POST("buyers/registration")
    Call<Buyer> registerBuyer(@Body RegisterBuyerDTO registerBuyerDTO);

    @GET("buyers")
    Call<List<Buyer>> getBuyers();

    @GET("buyers/{id}")
    Call<Buyer> getBuyer(@Path("id") Long id);
}
