package com.example.pmsu_project.models;

import java.util.HashSet;
import java.util.Set;

public class Article {
    private Long articleId;
    private String name;
    private String description;
    private Double price;
    private String imagePath;
    private Seller seller;
    private Set<Discount> discounts = new HashSet<>();
    private Set<ArticleQuantity> articleQuantities = new HashSet<>();

}
