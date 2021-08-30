package com.example.pmsu_project.models;

import java.util.HashSet;
import java.util.Set;

public class Article {
    private Long articleId;
    private String name;
    private String description;
    private Double price;
    private String imagePath;
    private Long seller;
    private boolean onSale;

    public Article(Long articleId, String name, String description, Double price, String imagePath, Long seller, boolean onSale) {
        this.articleId = articleId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.seller = seller;
        this.onSale = onSale;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", seller=" + seller +
                ", onSale=" + onSale +
                '}';
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
}
