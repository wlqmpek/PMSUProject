package com.example.pmsu_project.models;

import java.util.Set;

public class Buyer extends User {

    private String address;

    public Buyer(Long id, String firstName, String lastName, String username, boolean isBlocked, Set<Authority> authorities, String address) {
        super(id, firstName, lastName, username, isBlocked, authorities);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "address='" + address + '\'' +
                '}';
    }
}
