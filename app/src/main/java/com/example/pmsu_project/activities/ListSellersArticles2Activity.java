package com.example.pmsu_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.adapters.ListSellerArticlesAdapter;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSellersArticles2Activity extends AppCompatActivity {

    static final String TAG = ListSellersArticles2Activity.class.getSimpleName();

    private ArticleServices articleServices;
    private OrderServices orderServices;

    private List<Article> articles = new ArrayList<>();

    ListSellerArticlesAdapter listSellerArticlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sellers_articles2);

        articleServices = ApiUtils.getArticleService();

        articleServices.getArticlesFromSeller(LoggedUser.getUserId()).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    articles = response.body();

                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        });
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }
}