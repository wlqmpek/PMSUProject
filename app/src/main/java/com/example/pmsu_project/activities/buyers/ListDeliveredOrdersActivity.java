package com.example.pmsu_project.activities.buyers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.adapters.ListDeliveredOrdersAdapter;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.services.OrderServices;

import android.content.Context;
import android.content.Intent;
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

public class ListDeliveredOrdersActivity extends AppCompatActivity {


    static final String TAG = ListDeliveredOrdersActivity.class.getSimpleName();
    private OrderServices orderServices;
    private List<Order> orders = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context = this;

    ListDeliveredOrdersAdapter listDeliveredOrdersAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_delivered_orders);
        setSupportActionBar(findViewById(R.id.listDeliveredOrdersToolbar));
        Log.i(TAG, "List Delivered Orders Activity Started");
        orderServices = ApiUtils.getOrderService();

        recyclerView = findViewById(R.id.listDeliveredOrdersRecyclerView);
        listDeliveredOrdersAdapter = new ListDeliveredOrdersAdapter(this);
        recyclerView.setAdapter(listDeliveredOrdersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderServices.getDeliveredOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(response.isSuccessful()) {

                    orders = response.body();
                    listDeliveredOrdersAdapter.setOrders(orders);
                    listDeliveredOrdersAdapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

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
            Intent i = new Intent(ListDeliveredOrdersActivity.this, ListSellersActivity.class);
            context.startActivity(i);
        } else if(id == R.id.delivered) {
            Intent i = new Intent(ListDeliveredOrdersActivity.this, ListDeliveredOrdersActivity.class);
            context.startActivity(i);
//            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            LoggedUser.logout(this);
            Intent i = new Intent(ListDeliveredOrdersActivity.this, LoginActivity.class);
            context.startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}