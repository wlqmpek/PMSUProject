package com.example.pmsu_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveOrderFeedbackActivity extends AppCompatActivity {
    static final String TAG = ListDeliveredOrdersActivity.class.getSimpleName();
    private OrderServices orderServices;
    private ArticleServices articleServices;
    private RecyclerView recyclerView;
    private Context context = this;

    private List<Article> articles = new ArrayList<>();
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_order_feedback);
        setSupportActionBar(findViewById(R.id.leaveOrderFeedbackToolbar));
        Intent i = getIntent();
        order = (Order) i.getSerializableExtra("order");
        articleServices = ApiUtils.getArticleService();
        orderServices = ApiUtils.getOrderService();

        articleServices.getArticlesFromOrder(order.getOrderId()).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    articles = response.body();
                    for(Article article:articles) {
                        showResponse(article.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buyer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sellers) {
            Intent i = new Intent(LeaveOrderFeedbackActivity.this, ListSellersActivity.class);
            context.startActivity(i);
            showResponse("Sellers");
        } else if(id == R.id.delivered) {
            Intent i = new Intent(LeaveOrderFeedbackActivity.this, ListDeliveredOrdersActivity.class);
            context.startActivity(i);
//            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            showResponse("Logout");
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}