
package org.example.customer_app.model;

public class Publisher {
    private String publisherId;
    private String name;
    private String contactNumber;

    public Publisher(String publisherId) {
        this.publisherId = publisherId;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public String getPublisherId() { return publisherId; }
    public void setPublisherId(String publisherId) { this.publisherId = publisherId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}