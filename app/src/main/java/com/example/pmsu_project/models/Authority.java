package com.example.pmsu_project.models;

public class Authority {

    private Long authorityId;
    private String name;

    @Override
    public String toString() {
        return "Authority{" +
                "authorityId=" + authorityId +
                ", name='" + name + '\'' +
                '}';
    }

    public Authority() {
    }

    public Authority(Long authorityId, String name) {
        this.authorityId = authorityId;
        this.name = name;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
