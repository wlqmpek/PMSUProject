package com.example.pmsu_project.dtos;

import java.time.LocalDate;
import java.util.Set;

public class CreateDiscountDTO {

    private int precentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String text;
    private Set<Long> articles;

    public CreateDiscountDTO(int precentage, LocalDate startDate, LocalDate endDate, String text, Set<Long> articles) {
        this.precentage = precentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "CreateDiscountDTO{" +
                "precentage=" + precentage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", text='" + text + '\'' +
                ", articles=" + articles +
                '}';
    }

    public int getPrecentage() {
        return precentage;
    }

    public void setPrecentage(int precentage) {
        this.precentage = precentage;
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

    public Set<Long> getArticles() {
        return articles;
    }

    public void setArticles(Set<Long> articles) {
        this.articles = articles;
    }
}
