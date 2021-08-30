package com.example.pmsu_project.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {

    private Long orderId;

    private LocalDateTime time;
    private boolean delivered;
    private long buyer;
    private int rating;
    private String comment;
    private boolean anonymusComment;
    private boolean archivedComment;
    private List<ArticleQuantity> articleQuantity = new ArrayList<>();

    public Order(Long orderId, LocalDateTime time, boolean delivered, long buyer, int rating, String comment, boolean anonymusComment, boolean archivedComment, List<ArticleQuantity> articleQuantity) {
        this.orderId = orderId;
        this.time = time;
        this.delivered = delivered;
        this.buyer = buyer;
        this.rating = rating;
        this.comment = comment;
        this.anonymusComment = anonymusComment;
        this.archivedComment = archivedComment;
        this.articleQuantity = articleQuantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getBuyer() {
        return buyer;
    }

    public void setBuyer(long buyer) {
        this.buyer = buyer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isAnonymusComment() {
        return anonymusComment;
    }

    public void setAnonymusComment(boolean anonymusComment) {
        this.anonymusComment = anonymusComment;
    }

    public boolean isArchivedComment() {
        return archivedComment;
    }

    public void setArchivedComment(boolean archivedComment) {
        this.archivedComment = archivedComment;
    }

    public List<ArticleQuantity> getArticleQuantity() {
        return articleQuantity;
    }

    public void setArticleQuantity(List<ArticleQuantity> articleQuantity) {
        this.articleQuantity = articleQuantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", time=" + time +
                ", delivered=" + delivered +
                ", buyer=" + buyer +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", anonymusComment=" + anonymusComment +
                ", archivedComment=" + archivedComment +
                ", articleQuantity=" + articleQuantity +
                '}';
    }
}
