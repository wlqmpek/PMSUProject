package com.example.pmsu_project.activities.sellers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.adapters.ListSellerArticles2Adapter;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    RecyclerView recyclerView;

    ListSellerArticles2Adapter listSellerArticles2Adapter;

    private Context context = this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sellers_articles2);
        setSupportActionBar(findViewById(R.id.listSellerArticles2Toolbar));

        articleServices = ApiUtils.getArticleService();


        recyclerView = findViewById(R.id.listSellerArticlesRecyclerView2);
        listSellerArticles2Adapter = new ListSellerArticles2Adapter(this);
        recyclerView.setAdapter(listSellerArticles2Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleServices.getArticlesFromSeller(LoggedUser.getUserId()).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    articles = response.body();
                    showResponse(articles.size() + "");
                    listSellerArticles2Adapter.setArticles(articles);
                    listSellerArticles2Adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seller_menu, menu);
        return true;
    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.myArticles) {
            if(isStoragePermissionGranted()) {
                Intent i = new Intent(ListSellersArticles2Activity.this, ListSellersArticles2Activity.class);
                context.startActivity(i);
            } else {
                showResponse("We need storage access!");
            }
        } else if(id == R.id.createArticle) {
            if(isStoragePermissionGranted()) {
                Intent i = new Intent(ListSellersArticles2Activity.this, CreateArticleActivity.class);
                context.startActivity(i);
            } else {
                showResponse("We need storage access!");
            }

//            showResponse("Delivered");
        } else if(id == R.id.discount) {
            showResponse("Discount");
        } else if(id == R.id.commentManagment) {
            showResponse("Comment managment");
        } else if(id == R.id.logout) {
            showResponse("Logout");
            LoggedUser.logout(this);
            Intent i = new Intent(ListSellersArticles2Activity.this, LoginActivity.class);
            context.startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }


}