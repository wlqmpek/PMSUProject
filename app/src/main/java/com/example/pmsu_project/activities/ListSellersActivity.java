package com.example.pmsu_project.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.adapters.ListSellersRecycleViewAdapter;
import com.example.pmsu_project.models.Seller;
import com.example.pmsu_project.services.SellerServices;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSellersActivity extends AppCompatActivity {

    static final String TAG = ListSellersActivity.class.getSimpleName();

    private SellerServices sellerServices;

    private List<Seller> sellers = new ArrayList<>();

    private RecyclerView recyclerView;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sellers);
        setSupportActionBar(findViewById(R.id.listSellersToolbar));

        Log.i(TAG, "List Sellers Activity Started");

        sellerServices = ApiUtils.getSellerService();

        recyclerView = findViewById(R.id.listSellersRecyclerView);
        ListSellersRecycleViewAdapter listSellersRecycleViewAdapter = new ListSellersRecycleViewAdapter(context);
        recyclerView.setAdapter(listSellersRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        sellerServices.getSellers().enqueue(new Callback<List<Seller>>() {
            @Override
            public void onResponse(Call<List<Seller>> call, Response<List<Seller>> response) {
                if(response.isSuccessful()) {
                    sellers = response.body();
                    listSellersRecycleViewAdapter.setSellers(sellers);
                    listSellersRecycleViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Seller>> call, Throwable t) {

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
            showResponse("Sellers");
        } else if(id == R.id.delivered) {
            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            showResponse("Logout");
        }

//        return super.onOptionsItemSelected(item);
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
    }
}