package com.example.BankApp.resource;

import com.example.BankApp.model.BankAccount;
import com.fasterxml.jackson.annotation.JsonInclude;

public class BankAccountResource {
    private Integer id;
    private long numberBankAcc;
    private double balance;
    private Integer idClient;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientResource source;

    public  BankAccountResource(){}

    public BankAccountResource(BankAccount bankAccount){
        this.id = bankAccount.getId();
        this.numberBankAcc = bankAccount.getNumberBankAcc();
        this.balance = bankAccount.getBalance();
        this.idClient = bankAccount.getIdClient();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNumberBankAcc(long numberBankAcc){
        this.numberBankAcc = numberBankAcc;
    }
    public long getNumberBankAcc(){
        return numberBankAcc;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public double getBalance(){
        return balance;
    }
    public void setIdClient(Integer idClient){
        this.idClient = idClient;
    }
    public Integer getIdClient(){
        return idClient;
    }

    public ClientResource getSource() { return this.source; }

    public void setSource(ClientResource source) { this.source = source; }

    public BankAccount toEntity() {
        return new BankAccount(
                this.id,
                this.numberBankAcc,
                this.balance,
                this.idClient
        );
    }
}
