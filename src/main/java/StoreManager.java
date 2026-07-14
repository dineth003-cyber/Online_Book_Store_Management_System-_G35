package org.example.customer_app.model;

import org.example.customer_app.model.Staff;

public class StoreManager extends Staff {

    public StoreManager(String id, String name, double salary) {
        super(id, name, "Manager", salary);
    }
}