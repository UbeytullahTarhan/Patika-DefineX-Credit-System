package com.defineX.creditsystem.repository;

import com.defineX.creditsystem.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore,Integer> {

    CreditScore findCreditScoreByCustomer_IdentityNumber(String identity);
}
