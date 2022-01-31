package com.example.BankApp.resource;

import com.example.BankApp.model.Worker;

public class WorkerResource extends BaseResource{
    private Integer id;
    private String name;
    private String lastName;

    public WorkerResource() {}

    public WorkerResource(Worker worker) {
        this.id = worker.getId();
        this.name = worker.getName();
        this.lastName = worker.getLastName();
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

    public Worker toEntity() {
        return new Worker(
                this.id,
                this.name,
                this.lastName
        );
    }
}
