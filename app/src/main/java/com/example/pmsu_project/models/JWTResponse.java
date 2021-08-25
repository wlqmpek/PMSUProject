package com.example.pmsu_project.models;

public class JWTResponse {
    private String jwt;

    public JWTResponse(String jwt) {
        this.jwt = jwt;
    }

    public JWTResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
