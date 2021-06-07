package com.banking.controller;

import com.banking.entity.Entity;
import com.banking.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

abstract public class EntityController<T extends Entity> {
    @Autowired
    private PagingAndSortingRepository<T, String> repo;

    @GetMapping(path = "")
    public @ResponseBody Iterable<T> getEntities(
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "50") @Min(value = 1) @Max(value = 50) int size
    ) {
        Pageable request = PageRequest.of(page, size, Sort.by("id").ascending());
        return this.repo.findAll(request);
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody T getEntity(@PathVariable("id") String id) throws EntityNotFoundException {
        Optional<T> entity = this.repo.findById(id);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    @PostMapping(path = "")
    public @ResponseBody ResponseEntity<Map<String, String>> createEntity(@RequestBody T entity) {
        this.repo.save(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", entity.getId()));
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody ResponseEntity<Void> updateEntity(
            @Valid @RequestBody T entity,
            @PathVariable("id") String id
    ) throws EntityNotFoundException {
        if (!this.repo.existsById(id)) {
            throw new EntityNotFoundException();
        }

        entity.setId(id);
        this.repo.save(entity);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(
            ConstraintViolationException exception,
            ServletWebRequest webRequest
    ) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException exception,
                                                   ServletWebRequest webRequest) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
    }
}
