package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.exception.CreditScoreNotCalculatedException;
import com.defineX.creditsystem.service.CreditScoreService;
import com.defineX.creditsystem.service.utils.RandomUtilty;
import com.defineX.creditsystem.exception.CreditScoreNotValid;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreServiceImpl implements CreditScoreService {

    private final CreditScoreRepository creditScoreRepository;
    private final RandomUtilty randomUtilty;

    @Override
    public CreditScore addCreditScore(CreditScore creditScore) {
        if(creditScore.getCreditScore()<0){
            throw new CreditScoreNotValid();
        }
        return creditScoreRepository.save(creditScore);
    }

    @Override
    public CreditScore calculateCreditScore( ) {
        CreditScore entity = new CreditScore();
        int creditScore = randomUtilty.create0Between2000();
        entity.setCreditScore(creditScore);
        if(entity.getCreditScore()==null){
           throw new CreditScoreNotCalculatedException();
        }
        entity = creditScoreRepository.save(entity);
        return entity;
    }

    @Override
    public Integer getCreditScoreByCustomerIdentityNumber(String identityNumber) {
        CreditScore creditScore=creditScoreRepository.findCreditScoreByCustomer_IdentityNumber(identityNumber);
        if (creditScore==null){
            throw new NotFoundException("Credit Score");
        }
        return creditScore.getCreditScore();
    }

}
