package com.example.pmsu_project.models;

import com.auth0.android.jwt.JWT;
import com.example.pmsu_project.retrofit.RetrofitClient;

import java.util.ArrayList;

public class LoggedUser {
    private static String jwtToken;
    private static int userId;
    private static String username;
    private static Roles role;
    private static boolean loggedIn = false;

//    public LoggedUser(String jwtToken) {
//        this.jwtToken = jwtToken;
//    }

//    public static LoggedUser login(String jwtToken) {
//        JWT jwt = new JWT(jwtToken);
//        jwtToken = jwtToken;
//        this.userId = jwt.getClaim("userId").asInt();
//        this.username = jwt.getSubject();
//        this.roles.add(jwt.getClaim("roles").asArray(Roles.class)[0]);
//        System.out.println(this);
//
//        return this;
//    }

    public static void login(String jwtToken) {
        RetrofitClient.token = jwtToken;
        JWT jwt = new JWT(jwtToken);
        LoggedUser.setJwtToken(jwtToken);
        LoggedUser.setUserId(jwt.getClaim("userId").asInt());
        LoggedUser.setUsername(jwt.getSubject());
        LoggedUser.setRole(jwt.getClaim("roles").asArray(Roles.class)[0]);
        LoggedUser.setLoggedIn(true);
    }

    public void logout() {
        /// God help me.
        RetrofitClient.token = null;
        LoggedUser.setJwtToken(null);
        LoggedUser.setRole(null);
        LoggedUser.setUserId(0);
        LoggedUser.setUsername(null);
        LoggedUser.setLoggedIn(false);
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        LoggedUser.loggedIn = loggedIn;
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jwtToken) {
        LoggedUser.jwtToken = jwtToken;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        LoggedUser.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoggedUser.username = username;
    }

    public static Roles getRole() {
        return role;
    }

    public static void setRole(Roles role) {
        LoggedUser.role = role;
    }
}
