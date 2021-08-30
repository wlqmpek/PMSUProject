package com.example.pmsu_project.models;

public class Roles {
    private int authorityId;
    private String name;
    private String authority;

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "authorityId=" + authorityId +
                ", name='" + name + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

    public Roles(int authorityId, String name, String authority) {
        this.authorityId = authorityId;
        this.name = name;
        this.authority = authority;
    }
}
