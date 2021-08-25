package com.example.pmsu_project;

import com.example.pmsu_project.retrofit.RetrofitClient;
import com.example.pmsu_project.services.AuthServices;
import com.example.pmsu_project.services.BuyerService;
import com.example.pmsu_project.services.SellerService;

import retrofit2.http.PUT;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.158:8080/osa/";

    public static AuthServices getAuthService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthServices.class);
    }

    public static BuyerService getBuyerService() {
        return RetrofitClient.getClient(BASE_URL).create(BuyerService.class);
    }

    public static SellerService getSellerService() {
        return RetrofitClient.getClient(BASE_URL).create(SellerService.class);
    }
}
