package com.example.pmsu_project.retrofit;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.pmsu_project.LocalDateTimeDeserializer;
import com.example.pmsu_project.activities.buyers.ListDeliveredOrdersActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RetrofitClient {
    static final String TAG = RetrofitClient.class.getSimpleName();
    private static Retrofit retrofit = null;
    private static String baseUrlVar = null;
    public static String token = null;
    private static Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    public static Retrofit getClient(String baseUrl) {
        System.out.println("Getting retrofit + " );
        Log.i(TAG, "Retrofit  " + retrofit);
        Log.i(TAG, "Retrofit token " + token);
        baseUrlVar = baseUrl;
        if (retrofit==null) {

            if(token == null) {
                System.out.println("Getting retrofit 1");
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
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

            retrofit = new Retrofit.Builder().client(client).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }


}
