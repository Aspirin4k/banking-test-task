package com.banking.controller;

import com.banking.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;

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

    @PostMapping(path = "")
    public @ResponseBody String createEntity(@RequestBody T entity) {
        this.repo.save(entity);
        return entity.getId();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException exception,
                                                   ServletWebRequest webRequest) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
    }
}
