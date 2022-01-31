package com.example.BankApp.controller;

import com.example.BankApp.model.Client;
import com.example.BankApp.repository.ClientRepository;
import com.example.BankApp.resource.ClientResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin(origins = { "*"})
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ClientResource[] getAll(@RequestParam(required = false) Integer id) {
        Client[] entities = clientRepository.select();
        return Arrays.stream(entities)
                .map(entity -> {
                    ClientResource resource = new ClientResource(entity);
                    return resource;
                })
                .toArray(ClientResource[]::new);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ClientResource get(@PathVariable Integer id) {
        Client entity = clientRepository.select(id);
        if (entity == null) return null;
        ClientResource resource = new ClientResource(entity);
        return resource;
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    ClientResource post(@RequestBody ClientResource resource) {
        Client entity = clientRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new ClientResource(entity);
        return resource;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ClientResource put(@PathVariable Integer id,
                       @RequestBody ClientResource resource) {
        Client entity = clientRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new ClientResource(entity);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ClientResource delete(@PathVariable Integer id) {
        Client entity = clientRepository.delete(id);
        if (entity == null) return null;
        return new ClientResource(entity);
    }
}

