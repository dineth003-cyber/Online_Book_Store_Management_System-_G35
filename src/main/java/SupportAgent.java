package org.example.customer_app.model;

import org.example.customer_app.model.Staff;

public class SupportAgent extends Staff {

    public SupportAgent(String id, String name, double salary) {
        super(id, name, "Support", salary);
    }
}