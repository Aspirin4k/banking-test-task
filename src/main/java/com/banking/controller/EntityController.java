package com.banking.controller;

import com.banking.entity.Entity;
import com.banking.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

abstract public class EntityController<T extends Entity> {
    @Autowired
    private JpaRepository<T, String> repo;

    @GetMapping(path = "")
    public Iterable<T> getEntities(
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "50") @Min(value = 1) @Max(value = 50) int size
    ) {
        Pageable request = PageRequest.of(page, size, Sort.by("id").ascending());
        return this.repo.findAll(request);
    }

    @GetMapping(value = "/{id}")
    public T getEntity(@PathVariable("id") String id) throws EntityNotFoundException {
        Optional<T> entity = this.repo.findById(id);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    @PostMapping(path = "")
    public ResponseEntity<Map<String, String>> createEntity(
            @Valid @RequestBody T entity
    ) throws EntityNotFoundException {
        this.repo.save(entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", entity.getId()));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateEntity(
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable("id") String id) {
        if (this.repo.existsById(id)) {
            this.repo.deleteById(id);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
