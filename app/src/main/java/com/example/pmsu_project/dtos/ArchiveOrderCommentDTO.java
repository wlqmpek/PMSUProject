package com.example.pmsu_project.dtos;

public class ArchiveOrderCommentDTO {
    private boolean archivedComment;

    public ArchiveOrderCommentDTO(boolean archivedComment) {
        this.archivedComment = archivedComment;
    }

    @Override
    public String toString() {
        return "ArchiveOrderCommentDTO{" +
                "archivedComment=" + archivedComment +
                '}';
    }

    public boolean isArchivedComment() {
        return archivedComment;
    }

    public void setArchivedComment(boolean archivedComment) {
        this.archivedComment = archivedComment;
    }
}
