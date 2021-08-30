package com.example.pmsu_project.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmsu_project.ApiUtils;
import com.example.pmsu_project.R;
import com.example.pmsu_project.adapters.ListBuyerUndeliveredOrdersActivity;
import com.example.pmsu_project.adapters.ListFinishOrderArticlesAdapter;
import com.example.pmsu_project.adapters.ListSellerArticlesAdapter;
import com.example.pmsu_project.dtos.CreateArticleQuantityDTO;
import com.example.pmsu_project.dtos.CreateInitialOrderDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.Order;
import com.example.pmsu_project.models.Seller;
import com.example.pmsu_project.services.ArticleServices;
import com.example.pmsu_project.services.OrderServices;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button finishShoping;
    private Button order;

    ListSellerArticlesAdapter listSellerArticlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_seller_articles);
        Seller seller = (Seller) getIntent().getSerializableExtra("Seller");
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
                    Intent i=new Intent(ListSellerArticlesActivity.this, ListBuyerUndeliveredOrdersActivity.class);
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

    public void showResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
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