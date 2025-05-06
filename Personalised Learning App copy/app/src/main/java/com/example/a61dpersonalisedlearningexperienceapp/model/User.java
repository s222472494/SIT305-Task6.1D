package com.example.a61dpersonalisedlearningexperienceapp.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String interests;

    public User(String id, String name, String email, String interests) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.interests = interests;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getInterests() { return interests; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setInterests(String interests) { this.interests = interests; }
}

