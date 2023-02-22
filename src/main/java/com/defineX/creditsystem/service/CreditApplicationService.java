package com.defineX.creditsystem.service;

import com.defineX.creditsystem.entity.CreditApplication;

import java.util.List;

public interface CreditApplicationService {

    List<CreditApplication> getCreditApplication(String identityNumber);

    boolean addCreditApplication(String identityNumber);
}
