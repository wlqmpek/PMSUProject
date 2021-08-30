package com.example.pmsu_project.dtos;

public class CreateArticleQuantityDTO {

    private Long articleId;
    private int quantity;

    public CreateArticleQuantityDTO(Long articleId, int quantity) {
        this.articleId = articleId;
        this.quantity = quantity;
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
        return "CreateArticleQuantityDTO{" +
                "articleId=" + articleId +
                ", quantity=" + quantity +
                '}';
    }
}
