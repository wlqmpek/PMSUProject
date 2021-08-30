package com.example.pmsu_project.dtos;

import com.example.pmsu_project.models.ArticleQuantity;

import java.util.ArrayList;

public class CreateInitialOrderDTO {
    private ArrayList<CreateArticleQuantityDTO> articleQuantity = new ArrayList<>();

    public CreateInitialOrderDTO(ArrayList<CreateArticleQuantityDTO> articleQuantity) {
        this.articleQuantity = articleQuantity;
    }

    public CreateInitialOrderDTO() {
    }

    @Override
    public String toString() {
        return "CreateInitialOrderDTO{" +
                "articleQuantity=" + articleQuantity +
                '}';
    }

    public ArrayList<CreateArticleQuantityDTO> getArticleQuantity() {
        return articleQuantity;
    }

    public void setArticleQuantity(ArrayList<CreateArticleQuantityDTO> articleQuantity) {
        this.articleQuantity = articleQuantity;
    }
}
