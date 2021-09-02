package com.example.pmsu_project.activities.buyers;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.services.OrderServices;

import java.util.ArrayList;
import java.util.List;

public class ListUndeliveredOrdersActivity extends AppCompatActivity {

    static final String TAG = ListUndeliveredOrdersActivity.class.getSimpleName();

    private OrderServices orderServices;
    private List<Order> orders = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_undelivered_orders);

        orderServices = ApiUtils.getOrderService();

    }
;
    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    }
}
