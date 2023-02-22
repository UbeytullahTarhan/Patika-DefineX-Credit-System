package com.defineX.creditsystem.service;

import com.defineX.creditsystem.entity.CreditScore;

public interface CreditScoreService {

    CreditScore addCreditScore(CreditScore creditScore);

    CreditScore calculateCreditScore();

    Integer getCreditScoreByCustomerIdentityNumber(String identityNumber);
}
