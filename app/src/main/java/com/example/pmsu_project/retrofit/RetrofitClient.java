package com.example.pmsu_project.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static String baseUrlVar = null;
    public static String token = null;


    public static Retrofit getClient(String baseUrl) {
        System.out.println("Getting retrofit");
        baseUrlVar = baseUrl;
        if (retrofit==null) {

            if(token == null) {
                System.out.println("Getting retrofit 1");
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        if(token != null) {
            System.out.println("Getting retrofit 2");
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            }).build();
            retrofit = new Retrofit.Builder().client(client).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
