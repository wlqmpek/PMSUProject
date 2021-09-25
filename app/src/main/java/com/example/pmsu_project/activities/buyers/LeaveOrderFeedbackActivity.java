package com.example.pmsu_project.activities.buyers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.adapters.ListDeliveredArticlesAdapter;
import com.example.pmsu_project.dtos.CreateBuyersOrderFeedbackDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
    private EditText comment;
    private Spinner spinner;
    private Switch aSwitch;
    private Button buttonSendFeedback;


    private ListDeliveredArticlesAdapter listDeliveredArticlesAdapter;

    private List<Article> articles = new ArrayList<>();
    private Order order;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_order_feedback);
        setSupportActionBar(findViewById(R.id.leaveOrderFeedbackToolbar));
        Intent i = getIntent();
        order = (Order) i.getSerializableExtra("Order");
        showResponse(order.toString());
        articleServices = ApiUtils.getArticleService();
        orderServices = ApiUtils.getOrderService();
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ratingSpinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        recyclerView = findViewById(R.id.listDeliveredArticlesRecycleView);
        comment = findViewById(R.id.orderFeedbackMultiLineComment);
        spinner = findViewById(R.id.orderFeedbackSpinner);
        spinner.setAdapter(spinnerAdapter);
        aSwitch = findViewById(R.id.orderFeedbackSwitch);
        buttonSendFeedback = findViewById(R.id.orderFeedbackButton);

        listDeliveredArticlesAdapter = new ListDeliveredArticlesAdapter(this);
        recyclerView.setAdapter(listDeliveredArticlesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        articleServices.getArticlesFromOrder(order.getOrderId()).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    articles = response.body();
                    listDeliveredArticlesAdapter.setArticles(articles);
                    listDeliveredArticlesAdapter.notifyDataSetChanged();
                } else {
                    showResponse("No succes");
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                showResponse("No succes");
            }
        });

        orderServices.getOrder(order.getOrderId()).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()) {
                    order = response.body();
                    listDeliveredArticlesAdapter.setOrder(order);
                    listDeliveredArticlesAdapter.notifyDataSetChanged();
                } else {
                    showResponse("No succes");
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                showResponse("No succes");
            }
        });

        addSendFeedbackFunctionality();
    }

    public void addSendFeedbackFunctionality() {
        buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBuyersOrderFeedbackDTO createBuyersOrderFeedbackDTO = new CreateBuyersOrderFeedbackDTO(
                        comment.getText().toString(),
                        Integer.parseInt(spinner.getSelectedItem().toString()),
                        aSwitch.isChecked()
                );
                orderServices.buyerFeedback(order.getOrderId(), createBuyersOrderFeedbackDTO).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if(response.isSuccessful()) {
                            Intent i = new Intent(LeaveOrderFeedbackActivity.this, ListSellersActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buyer_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sellers) {
            showResponse("Sellers");
            Intent i = new Intent(LeaveOrderFeedbackActivity.this, ListSellersActivity.class);
            startActivity(i);
        } else if(id == R.id.delivered) {
            Intent i = new Intent(LeaveOrderFeedbackActivity.this, ListDeliveredOrdersActivity.class);
            startActivity(i);
            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            showResponse("Logout");
            LoggedUser.logout(this);
            Intent i = new Intent(LeaveOrderFeedbackActivity.this, LoginActivity.class);
            startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}