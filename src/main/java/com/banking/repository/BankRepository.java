package com.banking.repository;

import com.banking.entity.Bank;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankRepository extends PagingAndSortingRepository<Bank, String> {
}
