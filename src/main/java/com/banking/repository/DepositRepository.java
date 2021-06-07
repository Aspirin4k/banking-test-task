package com.banking.repository;

import com.banking.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DepositRepository extends JpaRepository<Deposit, String>, JpaSpecificationExecutor<Deposit> {
}
