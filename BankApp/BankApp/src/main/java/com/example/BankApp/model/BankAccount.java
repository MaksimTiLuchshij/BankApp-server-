package com.example.BankApp.model;

public class BankAccount extends BaseEntity {
    private long numberBankAcc;
    private double balance;
    private Integer idClient;

    public BankAccount(Integer id, long numberBankAcc, double balance, Integer idClient) {
        super(id);
        this.numberBankAcc = numberBankAcc;
        this.balance = balance;
        this.idClient = idClient;
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
    public Integer getIdClient(){ return idClient; }
}
