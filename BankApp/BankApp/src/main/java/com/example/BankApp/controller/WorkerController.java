package com.example.BankApp.controller;

import com.example.BankApp.model.Worker;
import com.example.BankApp.repository.WorkerRepository;
import com.example.BankApp.resource.WorkerResource;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    WorkerResource[] getAll(@RequestParam(required = false) Integer id) {
        Worker[] entities = workerRepository.select();
        return Arrays.stream(entities)
                .map(entity -> {
                    WorkerResource resource = new WorkerResource(entity);
                    return resource;
                })
                .toArray(WorkerResource[]::new);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    WorkerResource get(@PathVariable Integer id) {
        Worker entity = workerRepository.select(id);
        if (entity == null) return null;
        WorkerResource resource = new WorkerResource(entity);
        return resource;
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    WorkerResource post(@RequestBody WorkerResource resource) {
        Worker entity = workerRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new WorkerResource(entity);
        return resource;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    WorkerResource put(@PathVariable Integer id,
                       @RequestBody WorkerResource resource) {
        Worker entity = workerRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new WorkerResource(entity);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    WorkerResource delete(@PathVariable Integer id) {
        Worker entity = workerRepository.delete(id);
        if (entity == null) return null;
        return new WorkerResource(entity);
    }
}
