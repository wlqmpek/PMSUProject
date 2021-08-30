package com.example.pmsu_project.models;

import java.time.LocalDate;
import java.util.Set;

public class Discount {
    private Long discountId;
    private int percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String text;
    private Seller seller;
    private Set<Article> articles;

    public Discount(Long discountId, int percentage, LocalDate startDate, LocalDate endDate, String text, Seller seller, Set<Article> articles) {
        this.discountId = discountId;
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.seller = seller;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountId=" + discountId +
                ", percentage=" + percentage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", text='" + text + '\'' +
                ", seller=" + seller +
                ", articles=" + articles +
                '}';
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
