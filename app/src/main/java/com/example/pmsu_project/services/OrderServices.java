package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.ArchiveOrderCommentDTO;
import com.example.pmsu_project.dtos.CreateBuyersOrderFeedbackDTO;
import com.example.pmsu_project.dtos.CreateInitialOrderDTO;
import com.example.pmsu_project.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderServices {

    @GET("orders/{id}")
    Call<Order> getOrder(@Path("id") Long id);

    @GET("orders/undelivered")
    Call<List<Order>> getUnedeliveredOrders();

    @GET("orders/delivered")
    Call<List<Order>> getDeliveredOrders();

    @GET("orders/delivered/commented")
    Call<List<Order>> getDeliveredAndCommented();

    @POST("orders")
    Call<Order> createOrder(@Body CreateInitialOrderDTO createInitialOrderDTO);

    @PUT("orders/delivered/{id}")
    Call<Order> setDelivered(@Path("id") Long id);

    @PUT("orders/archived_comment/{id}")
    Call<Order> setArchiveComment(@Path("id") Long id, @Body ArchiveOrderCommentDTO archiveOrderCommentDTO);

    @PUT("orders/buyer_feedback/{id}")
    Call<Order> buyerFeedback(@Path("id") Long id, @Body CreateBuyersOrderFeedbackDTO createBuyersOrderFeedbackDTO);


}
