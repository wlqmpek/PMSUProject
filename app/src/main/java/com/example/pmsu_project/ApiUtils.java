package com.example.pmsu_project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.pmsu_project.retrofit.RetrofitClient;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.AuthServices;
import com.example.pmsu_project.services.BuyerServices;
import com.example.pmsu_project.services.OrderServices;
import com.example.pmsu_project.services.SellerServices;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.104:8080/osa/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AuthServices getAuthService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthServices.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static BuyerServices getBuyerService() {
        return RetrofitClient.getClient(BASE_URL).create(BuyerServices.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static SellerServices getSellerService() {
        return RetrofitClient.getClient(BASE_URL).create(SellerServices.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArticleServices getArticleService() {
        return RetrofitClient.getClient(BASE_URL).create(ArticleServices.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static OrderServices getOrderService() {
        return RetrofitClient.getClient(BASE_URL).create(OrderServices.class);
    }
}
