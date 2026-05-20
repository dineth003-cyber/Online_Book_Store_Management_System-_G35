package org.example.customer_app.model;

public class RegisteredUser {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    // Constructor එක (Object එක හදද්දි දත්ත ඇතුළත් කරන්න)
    public RegisteredUser(String id, String name, String email, String password, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters (දත්ත ගන්න සහ වෙනස් කරන්න)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
