package com.example.BankApp.resource;

import com.example.BankApp.model.Client;

public class ClientResource extends BaseResource{
    private Integer id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String e_mail;

    public ClientResource() {}

    public ClientResource(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.phoneNumber = client.getPhoneNumber();
        this.e_mail = client.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setEmail(String email){ this.e_mail = email; }
    public String getEmail(){
        return e_mail;
    }

    public Client toEntity() {
        return new Client(
                this.id,
                this.name,
                this.lastName,
                this.phoneNumber,
                this.e_mail
        );
    }
}
