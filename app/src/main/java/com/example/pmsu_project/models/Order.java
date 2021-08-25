package com.example.pmsu_project.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Order {

    private Long orderId;

    private LocalDateTime time;

    private boolean delivered;

    private int rating;

    private String comment;

    private boolean anonymusComment;

    private boolean archivedComment;

    private Buyer buyer;

    private Set<ArticleQuantity> articleQuantity = new HashSet<>();
}
