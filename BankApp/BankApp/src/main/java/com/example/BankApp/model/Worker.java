package com.example.BankApp.model;

public class Worker extends BaseEntity {
    private String name;
    private String lastname;

    public Worker(Integer id, String name, String lastname) {
        super(id);
        this.name = name;
        this.lastname = lastname;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getLastName() {
        return lastname;
    }
}

