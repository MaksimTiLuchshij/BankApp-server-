package com.example.BankApp.controller;

import com.example.BankApp.model.BankAccount;
import com.example.BankApp.repository.BankAccountRepository;
import com.example.BankApp.repository.ClientRepository;
import com.example.BankApp.resource.BankAccountResource;
import com.example.BankApp.resource.ClientResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/bankaccounts")
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    private final ClientRepository clientRepository;



    public BankAccountController(BankAccountRepository bankAccountRepository, ClientRepository clientRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    BankAccountResource[] getAll(@RequestParam(required = false) Integer clientId,
                                 @RequestParam(required = false) Object expand) {
        BankAccount[] entities = clientId == null ?
                bankAccountRepository.select() :
                bankAccountRepository.selectByClientId(clientId);
        return Arrays.stream(entities)
                .map(entity -> {
                    BankAccountResource resource = new BankAccountResource(entity);
                    if (expand != null)
                        resource.setSource(new ClientResource(
                                clientRepository.select(entity.getIdClient()))
                        );
                    return resource;
                })
                .toArray(BankAccountResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    BankAccountResource get(@PathVariable Integer id,
                        @RequestParam(required = false) Object expand) {
        BankAccount entity = bankAccountRepository.select(id);
        if (entity == null) return null;
        BankAccountResource resource = new BankAccountResource(entity);
        if (expand != null)
            resource.setSource(
                    new ClientResource(clientRepository.select(entity.getIdClient()))
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    BankAccountResource post(@RequestBody BankAccountResource resource) {
        BankAccount entity = bankAccountRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new BankAccountResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    BankAccountResource put(@PathVariable Integer id,
                        @RequestBody BankAccountResource resource) {
        BankAccount entity = bankAccountRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new BankAccountResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    BankAccountResource delete(@PathVariable Integer id) {
        BankAccount entity = bankAccountRepository.delete(id);
        if (entity == null) return null;
        BankAccountResource resource = new BankAccountResource(entity);
        return resource;
    }
}
