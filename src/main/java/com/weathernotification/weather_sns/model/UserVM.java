package com.weathernotification.weather_sns.model;

public class UserVM {

    private String username;
    private String email;
    private String location;

    public UserVM() {}

    public UserVM(String username, String email, String location) {
        this.username = username;
        this.email = email;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
