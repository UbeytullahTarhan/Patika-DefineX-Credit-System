package com.defineX.creditsystem.entity.mapper;

import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.Customer;

public class CreditApplicationMapper {

    public static CreditApplication mapCreditApplication(Customer customer, Float creditLimit, boolean status ){
        CreditApplication creditApplication1=new CreditApplication();
        creditApplication1.setStatus(status);
        creditApplication1.setCreditLimit(creditLimit);
        creditApplication1.setCustomer(customer);

        if(creditApplication1.getCreditLimit()==0){
            creditApplication1.setStatus(false);
        }
        return creditApplication1;
    }


}
