package com.example.BankApp.controller;

import com.example.BankApp.model.Transaction;
import com.example.BankApp.repository.BankAccountRepository;
import com.example.BankApp.repository.TransactionRepository;
import com.example.BankApp.repository.WorkerRepository;
import com.example.BankApp.resource.BankAccountResource;
import com.example.BankApp.resource.TransactionResource;
import com.example.BankApp.resource.WorkerResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final WorkerRepository workerRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionController(TransactionRepository transactionRepository, WorkerRepository workerRepository,
                                 BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.workerRepository = workerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    TransactionResource[] getAll(@RequestParam(required = false) Integer worker_id,
                             @RequestParam(required = false) Object expand) {
        Transaction[] entities = worker_id == null ?
                transactionRepository.select() :
                transactionRepository.selectByWorkerId(worker_id);
        return Arrays.stream(entities)
                .map(entity -> {
                    TransactionResource resource = new TransactionResource(entity);
                    if (expand != null)
                        resource.setSource(new WorkerResource(
                                workerRepository.select(entity.getIdWorker())),
                                new BankAccountResource(bankAccountRepository.select(entity.getIdBankAccount()))
                        );
                    return resource;
                })
                .toArray(TransactionResource[]::new);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TransactionResource get(@PathVariable Integer id,
                        @RequestParam(required = false) Object expand) {
        Transaction entity = transactionRepository.select(id);
        if (entity == null) return null;
        TransactionResource resource = new TransactionResource(entity);
        if (expand != null) {
            resource.setSource(
                    new WorkerResource(workerRepository.select(entity.getIdWorker())),
                    new BankAccountResource(bankAccountRepository.select(entity.getIdBankAccount()))
            );

        }
        return resource;
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    TransactionResource post(@RequestBody TransactionResource resource) {
        double balance = bankAccountRepository.select(resource.getIdBankAccount()).getBalance();
        double amount = resource.getAmount();
        double result;
        if((resource.getStatus().equals("-"))) {
            result = balance - amount;
            if (result > 0) {
                Transaction entity = transactionRepository.insert(resource.toEntity());
                result = balance - amount;
                bankAccountRepository.updateBalance(entity.getIdBankAccount(), result);
                if (entity == null) return null;
                resource = new TransactionResource(entity);
                return resource;
            }
            else
                return null;
        }
        if(resource.getStatus().equals("+")) {
            Transaction entity = transactionRepository.insert(resource.toEntity());
            result = balance + amount;
            bankAccountRepository.updateBalance(entity.getIdBankAccount(), result);
            if (entity == null) return null;
            resource = new TransactionResource(entity);
            return resource;
        }
        else
            return null;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TransactionResource put(@PathVariable Integer id,
                       @RequestBody TransactionResource resource) {
        Transaction entity = transactionRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new TransactionResource(entity);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    TransactionResource delete(@PathVariable Integer id) {
        Transaction entity = transactionRepository.delete(id);
        if (entity == null) return null;
        return new TransactionResource(entity);
    }
}