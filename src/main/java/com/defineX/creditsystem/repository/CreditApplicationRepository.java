package com.defineX.creditsystem.repository;

import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication,Integer> {

    List<CreditApplication> findByCustomer(Customer customer);
    List<CreditApplication> findByCustomer_IdentityNumber(String identityNumber);
}
