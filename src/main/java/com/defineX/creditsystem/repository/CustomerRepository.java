package com.defineX.creditsystem.repository;

import com.defineX.creditsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByIdentityNumber(String identityNumber);

}

