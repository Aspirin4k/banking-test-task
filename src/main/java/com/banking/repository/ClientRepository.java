package com.banking.repository;

import com.banking.entity.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, String> {
}
