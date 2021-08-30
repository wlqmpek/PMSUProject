package com.example.pmsu_project.models;

public class ArticleQuantity {

    private Long articleQuantityId;
    private Long orderId;
    private Long articleId;
    private int quantity;

    public ArticleQuantity(Long articleQuantityId, Long orderId, Long articleId, int quantity) {
        this.articleQuantityId = articleQuantityId;
        this.orderId = orderId;
        this.articleId = articleId;
        this.quantity = quantity;
    }

    public Long getArticleQuantityId() {
        return articleQuantityId;
    }

    public void setArticleQuantityId(Long articleQuantityId) {
        this.articleQuantityId = articleQuantityId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ArticleQuantity{" +
                "articleQuantityId=" + articleQuantityId +
                ", orderId=" + orderId +
                ", articleId=" + articleId +
                ", quantity=" + quantity +
                '}';
    }
}
