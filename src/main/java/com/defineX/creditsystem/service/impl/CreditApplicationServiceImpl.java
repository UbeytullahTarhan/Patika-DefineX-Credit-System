package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.mapper.CreditApplicationMapper;
import com.defineX.creditsystem.service.CreditApplicationService;
import com.defineX.creditsystem.service.CreditScoreService;
import com.defineX.creditsystem.service.CustomerService;
import com.defineX.creditsystem.service.MessageService;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.repository.CreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditApplicationServiceImpl implements CreditApplicationService {

    private final CustomerService customerService;
    private final CreditApplicationRepository creditApplicationRepository;
    private final MessageService messageService;
    private final CreditScoreService creditScoreService;

    @Override
    public List<CreditApplication> getCreditApplication(String identityNumber) {
        List<CreditApplication>creditApplications= creditApplicationRepository.findByCustomer_IdentityNumber(identityNumber);
        if(creditApplications.size()==0){
            throw new NotFoundException("Customer that has this identity number");
        }
        return creditApplications;
    }

    @Override
    public boolean addCreditApplication(String identityNumber) {
        Customer customer=customerService.getCustomerByIdentityNumber(identityNumber);
        CreditApplication creditApplication= CreditApplicationMapper.mapCreditApplication(customer,calculateCreditLimit(customer),true);

        creditApplication= creditApplicationRepository.save(creditApplication);

        if(!creditApplication.isStatus()){
            messageService.sendMessage(customer.getPhone(),false,creditApplication.getCreditLimit());
        }
        else{
            messageService.sendMessage(customer.getPhone(),true,creditApplication.getCreditLimit());
        }
        List<CreditApplication> credits=customerService.getCustomerByIdentityNumber(identityNumber).getCredits();
        credits.add(creditApplication);
        customer.setCredits(credits);
        customerService.updateCustomer(customer.getId(),customer);
        return true;
    }

    private Float calculateCreditLimit(Customer customer){
        Integer creditScore=creditScoreService.getCreditScoreByCustomerIdentityNumber(customer.getIdentityNumber());
        Float limit=0.0F;
        if(creditScore<500){

            return limit;
        }
        else if(creditScore>500 && creditScore<1000){
           if(customer.getMonthlyIncome()<5000){
               limit=10000.0F;
           }
           else{
               limit=20000.0F;
           }
        }
        else {
           limit=customer.getMonthlyIncome()*4;
        }
        return limit;
    }
}
