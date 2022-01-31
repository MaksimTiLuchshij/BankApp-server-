package com.example.BankApp.model;

public class Transaction extends BaseEntity{
    private Integer idWorker;
    private Integer idBankAccount;
    private double amount;
    private String status;

    public Transaction(Integer id, Integer idWorker, Integer idBankAccount, double amount, String status) {
        super(id);
        this.idWorker = idWorker;
        this.idBankAccount = idBankAccount;
        this.amount = amount;
        this.status = status;
    }

    public void setIdWorker(Integer idWorker){
        this.idWorker = idWorker;
    }
    public Integer getIdWorker(){
        return idWorker;
    }
    public void setIdBankAccount(Integer idBankAccount){
        this.idBankAccount = idBankAccount;
    }
    public Integer getIdBankAccount(){
        return idBankAccount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return amount;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}