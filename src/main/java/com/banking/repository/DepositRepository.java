package com.banking.repository;

import com.banking.entity.Deposit;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepositRepository extends PagingAndSortingRepository<Deposit, String> {
}
