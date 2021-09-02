package com.example.pmsu_project.services;

import com.example.pmsu_project.dtos.CreateArticleDTO;
import com.example.pmsu_project.models.Article;
import com.example.pmsu_project.models.Picture;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ArticleServices {

    @GET("articles")
    Call<List<Article>> getArticles();

    @GET("articles/seller/{id}")
    Call<List<Article>> getArticlesFromSeller(@Path("id") Long id);

    @GET("articles/order/{id}")
    Call<List<Article>> getArticlesFromOrder(@Path("id") Long id);

    @GET("articles/{id}")
    Call<Article> getArticle(@Path("id") Long id);

    @POST("articles")
    Call<Article> createArticle(@Body CreateArticleDTO createArticleDTO);

    @PUT("articles/{id}")
    Call<Article> updateArticle(@Path("id") Long id, @Body CreateArticleDTO createArticleDTO);

    @Multipart
    @POST("articles/picture")
    Call<Long> saveImage(@Part MultipartBody.Part image);

    @DELETE("articles/{id}")
    Call<Response> deleteArticle(@Path("id") Long id);

    @GET("articles/picture/{id}")
    Call<Picture> getArticleImage(@Path("id") Long id);
}
