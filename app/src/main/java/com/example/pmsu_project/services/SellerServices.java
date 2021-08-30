package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.RegisterSellerDTO;
import com.example.pmsu_project.models.Seller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SellerServices {
    @POST("sellers/registration")
    Call<Seller> registerSeller(@Body RegisterSellerDTO registerSellerDTO);

    @GET("sellers")
    Call<List<Seller>> getSellers();

    @GET("sellers/{id}")
    Call<Seller> getSeller(@Path("id") Long id);

}
