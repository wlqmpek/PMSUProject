package com.example.pmsu_project.models;

import java.util.Set;

public class Admin extends User {
    public Admin(Long id, String firstName, String lastName, String username, String password, boolean isBlocked, Set<Authority> authorities) {
        super(id, firstName, lastName, username, isBlocked, authorities);
    }
}
