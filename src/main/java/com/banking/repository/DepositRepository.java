package com.banking.repository;

import com.banking.entity.Deposit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepositRepository extends PagingAndSortingRepository<Deposit, String> {
}
