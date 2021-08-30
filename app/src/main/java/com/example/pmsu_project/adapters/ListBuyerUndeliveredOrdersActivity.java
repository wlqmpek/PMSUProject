package com.example.pmsu_project.adapters;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.ListSellerArticlesActivity;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.services.OrderServices;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBuyerUndeliveredOrdersActivity extends AppCompatActivity {

    static final String TAG = ListBuyerUndeliveredOrdersActivity.class.getSimpleName();

    private OrderServices orderServices;
    private List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buyer_undelivered_orders);

        orderServices = ApiUtils.getOrderService();

        orderServices.getUnedeliveredOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "Success " + response.code());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }
}