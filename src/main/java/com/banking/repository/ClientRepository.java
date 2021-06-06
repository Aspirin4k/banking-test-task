package com.banking.repository;

import com.banking.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends PagingAndSortingRepository<Client, String> {
}
