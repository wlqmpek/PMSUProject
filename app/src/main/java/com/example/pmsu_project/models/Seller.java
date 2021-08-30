package com.example.pmsu_project.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Seller extends User implements Serializable {
    private LocalDate registrationDate;
    private String email;
    private String address;
    private String name;
    private double rating;

    public Seller(Long userId, String firstName, String lastName, String username, boolean isBlocked, Set<Authority> authorities, LocalDate registrationDate, String email, String address, String name, double rating) {
        super(userId, firstName, lastName, username, isBlocked, authorities);
        this.registrationDate = registrationDate;
        this.email = email;
        this.address = address;
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "registrationDate=" + registrationDate +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
