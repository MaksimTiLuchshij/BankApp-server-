package com.example.BankApp.resource;

import com.example.BankApp.model.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;

public class TransactionResource extends BaseResource{
    private Integer id;
    private Integer idWorker;
    private Integer idBankAccount;
    private double amount;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WorkerResource workerResource;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BankAccountResource bankAccountResource;

    public TransactionResource() {}

    public TransactionResource(Transaction transaction) {
        this.id = transaction.getId();
        this.idWorker = transaction.getIdWorker();
        this.idBankAccount = transaction.getIdBankAccount();
        this.amount = transaction.getAmount();
        this.status = transaction.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public void setSource(WorkerResource workerResource, BankAccountResource bankAccountResource) {
        this.workerResource = workerResource;
        this.bankAccountResource = bankAccountResource;
    }
    public WorkerResource getWorkerResource() { return this.workerResource; }
    public BankAccountResource getBankAccountResource(){ return bankAccountResource; };
    public Transaction toEntity() {
        return new Transaction(
                this.id,
                this.idWorker,
                this.idBankAccount,
                this.amount,
                this.status
        );
    }
}