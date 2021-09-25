package com.example.pmsu_project.activities.buyers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.activities.LoginActivity;
import com.example.pmsu_project.adapters.ListFinishOrderArticlesAdapter;
import com.example.pmsu_project.adapters.ListSellerArticlesAdapter;
import com.example.pmsu_project.dtos.CreateArticleQuantityDTO;
import com.example.pmsu_project.dtos.CreateInitialOrderDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.LoggedUser;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.models.Seller;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSellerArticlesActivity extends AppCompatActivity {

    static final String TAG = ListSellerArticlesActivity.class.getSimpleName();

    private ArticleServices articleServices;
    private OrderServices orderServices;

    private List<Article> articles = new ArrayList<>();
    private CreateInitialOrderDTO initialOrderDTO = new CreateInitialOrderDTO();

    RecyclerView recyclerView;
    List<EditText> recycleViewEditFields = new ArrayList<>();

    private Context context;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button finishShoping;
    private Button order;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    ListSellerArticlesAdapter listSellerArticlesAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_seller_articles);
        setSupportActionBar(findViewById(R.id.listSellerArticlesToolbar));
        Seller seller = (Seller) getIntent().getSerializableExtra("Seller");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
//        showResponse(seller.toString());

        articleServices = ApiUtils.getArticleService();
        orderServices = ApiUtils.getOrderService();

        finishShoping = findViewById(R.id.listArticleOrderButton);
        addFinishShopingButtonFunctionality();

        recyclerView = findViewById(R.id.listSellerArticlesRecyclerView);
        listSellerArticlesAdapter = new ListSellerArticlesAdapter(this);
        recyclerView.setAdapter(listSellerArticlesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleServices.getArticlesFromSeller(seller.getId()).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "Success " + response.code());
                    articles = response.body();
                    listSellerArticlesAdapter.setArticles(articles);
                    listSellerArticlesAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, "Fail " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.i(TAG, "FAIL X2 " +t);
            }
        });


    }

    private void addFinishShopingButtonFunctionality() {
        finishShoping.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showResponse("Cicked");
                createFinishOrderDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createFinishOrderDialog() {
        // Clearing in order not to order same article twice.
        initialOrderDTO.getArticleQuantity().clear();
        recycleViewEditFields = listSellerArticlesAdapter.getEditTexts();


        // Adding check and functionality
//        checkValidityOfTheInputFieldsAndEditOrderButtonProperty();
//        addFunctionalityForEveryInputField();

        // Check what items that buyer selected should be converted as a ArticleQuantaty.
        for(EditText editText:recycleViewEditFields) {
            if(!editText.getText().toString().isEmpty() && Integer.parseInt(editText.getText().toString()) > 0) {
                initialOrderDTO.getArticleQuantity().add(
                        new CreateArticleQuantityDTO(articles.get(recycleViewEditFields.indexOf(editText)).getArticleId(),
                                                Integer.parseInt(editText.getText().toString())));
            }
        }

        // Creating AlertDialog
        dialogBuilder = new AlertDialog.Builder(this);
        final View finishOrderPopupView = getLayoutInflater().inflate(R.layout.finish_order_popup, null);
        dialogBuilder.setView(finishOrderPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        // Setting up recyclerViewAdapter that is inside the Popup.
        RecyclerView recyclerView1 = dialog.findViewById(R.id.listFinishOrderArticleRecycleView);
        ListFinishOrderArticlesAdapter finishOrderArticlesAdapter = new ListFinishOrderArticlesAdapter(this);
        recyclerView1.setAdapter(finishOrderArticlesAdapter);
        finishOrderArticlesAdapter.setCreateInitialOrderDTO(initialOrderDTO);
        finishOrderArticlesAdapter.setArticles(articles);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        finishOrderArticlesAdapter.notifyDataSetChanged();
        TextView total = dialog.findViewById(R.id.finishOrderTotalTextView);
        total.setText(String.valueOf(finishOrderArticlesAdapter.calculateTotal()));

        order = dialog.findViewById(R.id.finishOrderPopupConfirmationButton);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });

    }

    private void createOrder() {
        orderServices.createOrder(initialOrderDTO).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "POST submited to API Successfully " + response.code());
                    showResponse(response.body().toString());
                    Intent i=new Intent(ListSellerArticlesActivity.this, ListUndeliveredOrdersActivity.class);
                    startActivity(i);
                } else {
                    Log.i(TAG, "POST submited to API Sucessffully code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.i(TAG, "POST submited to API Failed " + t);
                showResponse("Check your internet connection. " + t);
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
            Intent i = new Intent(ListSellerArticlesActivity.this, ListSellersActivity.class);
            startActivity(i);
        } else if(id == R.id.delivered) {
            Intent i = new Intent(ListSellerArticlesActivity.this, ListDeliveredOrdersActivity.class);
            startActivity(i);
            showResponse("Delivered");
        } else if(id == R.id.undelivered) {
            showResponse("Undelivered");
        } else if(id == R.id.logout) {
            showResponse("Logout");
            LoggedUser.logout(this);
            Intent i = new Intent(ListSellerArticlesActivity.this, LoginActivity.class);
            startActivity(i);
        }
        return true;
    }

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                createFinishOrderDialog();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

//    public void checkValidityOfTheInputFieldsAndEditOrderButtonProperty() {
//        boolean validity = true;
//        for(EditText editText:recycleViewEditFields) {
//            if(editText.getText().toString().isEmpty() || Integer.parseInt(editText.getText().toString()) <= 0) {
//                validity = false;
//                break;
//            }
//        }
//        showResponse(String.valueOf(validity));
//        orderButton.setEnabled(validity);
//    }

//    public void addFunctionalityForEveryInputField() {
//        showResponse(recycleViewEditFields.size() + " Size");
//        for(EditText editText:recycleViewEditFields) {
//
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    checkValidityOfTheInputFieldsAndEditOrderButtonProperty();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//        }
//    }
}