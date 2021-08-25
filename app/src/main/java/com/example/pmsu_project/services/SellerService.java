package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.RegisterSellerDTO;
import com.example.pmsu_project.models.Seller;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SellerService {
    @POST("sellers/registration")
    Call<Seller> registerSeller(@Body RegisterSellerDTO registerSellerDTO);
}
