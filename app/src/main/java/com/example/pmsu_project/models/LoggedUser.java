package com.example.pmsu_project.models;

import com.auth0.android.jwt.JWT;

import java.util.ArrayList;

public class LoggedUser {
    private String jwtToken;
    private int userId;
    private String username;
    private ArrayList<Roles> roles = new ArrayList<>();

    public LoggedUser(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LoggedUser login(String jwtToken) {
        JWT jwt = new JWT(jwtToken);
        this.jwtToken = jwtToken;
        this.userId = jwt.getClaim("userId").asInt();
        this.username = jwt.getSubject();
        this.roles.add(jwt.getClaim("roles").asArray(Roles.class)[0]);
        System.out.println(this);

        return this;
    }

    public void logout() {

    }

    @Override
    public String toString() {
        return "LoggedUser{" +
                "jwtToken='" + jwtToken + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Roles> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Roles> roles) {
        this.roles = roles;
    }
}
