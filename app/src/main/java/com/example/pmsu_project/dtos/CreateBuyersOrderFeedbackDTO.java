package com.example.pmsu_project.dtos;

public class CreateBuyersOrderFeedbackDTO {
    private String comment;
    private int rating;
    private boolean anonymusComment;

    public CreateBuyersOrderFeedbackDTO(String comment, int rating, boolean anonymusComment) {
        this.comment = comment;
        this.rating = rating;
        this.anonymusComment = anonymusComment;
    }

    @Override
    public String toString() {
        return "CreateBuyersOrderFeedbackDTO{" +
                "comment='" + comment + '\'' +
                ", rating=" + rating +
                ", anonymusComment=" + anonymusComment +
                '}';
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAnonymusComment() {
        return anonymusComment;
    }

    public void setAnonymusComment(boolean anonymusComment) {
        this.anonymusComment = anonymusComment;
    }
}
