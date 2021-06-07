package com.banking.repository;

import com.banking.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bank", path = "bank")
public interface BankRepository extends JpaRepository<Bank, String>, JpaSpecificationExecutor<Bank> {
}
