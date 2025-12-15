package com.example;

public class Patron {

    private final int id;
    private final String name;
    private final String email;

    public Patron(int id, String name, String email) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name required");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email required");

        this.id = id;
        this.name = name.trim();
        this.email = email.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

}
