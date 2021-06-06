package com.banking.repository;

import com.banking.entity.Bank;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BankRepository extends PagingAndSortingRepository<Bank, String> {
}
