package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.mapper.CreditApplicationMapper;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.repository.CreditApplicationRepository;
import com.defineX.creditsystem.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceImplTest {

    @Mock
    Customer customer;

    @Mock
    CreditScore creditScore;

    @Mock
    CustomerServiceImpl customerService;

    @Mock
    CreditScoreService creditScoreService;

    @Mock
    CreditApplicationMapper creditApplicationMapper;

    @Mock
    MessageServiceImpl messageService;

    @Mock
    CreditApplicationRepository creditApplicationRepository;

    @InjectMocks
    CreditApplicationServiceImpl creditApplicationService;

    @Test
    void getCreditApplication_successful() {

        Customer expectedCustomer=new Customer(1,"123","Ubeytullah","Tarhan",100.0F,"05767007070",null,null);
        CreditApplication creditApplication1=new CreditApplication(1,true,10000.0F,expectedCustomer);
        CreditApplication creditApplication2=new CreditApplication(1,true,10000.0F,expectedCustomer);

        List<CreditApplication> expectedCreditApplications =new ArrayList<>();
        expectedCreditApplications.add(creditApplication1);
        expectedCreditApplications.add(creditApplication2);

        when(creditApplicationRepository.findByCustomer_IdentityNumber("123")).thenReturn(expectedCreditApplications);

        List<CreditApplication> actualCreditApplication=creditApplicationService.getCreditApplication("123");

        assertEquals(expectedCreditApplications.size(),actualCreditApplication.size());

    }
    @Test
    void getCreditApplication_not_found() {
        List<CreditApplication>creditApplications = Collections.<CreditApplication>emptyList();
        when(creditApplicationRepository.findByCustomer_IdentityNumber(any())).thenReturn(creditApplications);

        assertThrows(NotFoundException.class,
                ()->{
                   List<CreditApplication>  actualCreditApplication=creditApplicationService.getCreditApplication(any());
                });
    }


    @Test
    void addCreditApplication_successful_limit_10000() {
        CreditApplication creditApplication=new CreditApplication(1,true,10000.0F,null);
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication);
        CreditScore creditScore=new CreditScore(1,750,null);
        Customer expectedCustomer=new Customer(1,"12345678975","test","test",1000.0F,"05703453434",creditScore,creditApplications);

        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(messageService.sendMessage(expectedCustomer.getPhone(),creditApplication.isStatus(), creditApplication.getCreditLimit())).thenReturn("success message");
        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(customerService.updateCustomer(1,expectedCustomer)).thenReturn(expectedCustomer);

       boolean actualStatus= creditApplicationService.addCreditApplication("12345678975");

        assertEquals(creditApplication.getCreditLimit(),10000.0F);
        assertTrue(actualStatus);
    }
    @Test
    void addCreditApplication_successful_limit_20000() {
        CreditApplication creditApplication=new CreditApplication(1,true,20000.0F,null);
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication);
        CreditScore creditScore=new CreditScore(1,750,null);
        Customer expectedCustomer=new Customer(1,"12345678975","test","test",9000.0F,"05703453434",creditScore,creditApplications);

        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(messageService.sendMessage(expectedCustomer.getPhone(),creditApplication.isStatus(), creditApplication.getCreditLimit())).thenReturn("success message");
        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(customerService.updateCustomer(1,expectedCustomer)).thenReturn(expectedCustomer);

        boolean actualStatus= creditApplicationService.addCreditApplication("12345678975");

        assertEquals(creditApplication.getCreditLimit(),20000.0F);
        assertTrue(actualStatus);
    }

    @Test
    void addCreditApplication_successful_limit_monthlyIncome_multiply_4() {
        CreditApplication creditApplication=new CreditApplication(1,true,36000.0F,null);
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication);
        CreditScore creditScore=new CreditScore(1,1100,null);
        Customer expectedCustomer=new Customer(1,"12345678975","test","test",9000.0F,"05703453434",creditScore,creditApplications);

        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(creditScoreService.getCreditScoreByCustomerIdentityNumber(any())).thenReturn(1100);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(messageService.sendMessage(expectedCustomer.getPhone(),creditApplication.isStatus(), creditApplication.getCreditLimit())).thenReturn("success message");
        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(customerService.updateCustomer(1,expectedCustomer)).thenReturn(expectedCustomer);

        boolean actualStatus= creditApplicationService.addCreditApplication("12345678975");
        assertEquals(creditApplication.getCreditLimit(),36000.0F);
        assertTrue(actualStatus);
    }

    @Test
    void addCreditApplication_successful_limit_0() {
        CreditApplication creditApplication=new CreditApplication(1,false,0.0F,null);
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication);
        CreditScore creditScore=new CreditScore(1,300,null);
        Customer expectedCustomer=new Customer(1,"12345678975","test","test",9000.0F,"05703453434",creditScore,creditApplications);

        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(creditScoreService.getCreditScoreByCustomerIdentityNumber(any())).thenReturn(300);
        when(creditApplicationRepository.save(any())).thenReturn(creditApplication);
        when(messageService.sendMessage(expectedCustomer.getPhone(),creditApplication.isStatus(), creditApplication.getCreditLimit())).thenReturn("success message");
        when(customerService.getCustomerByIdentityNumber(any())).thenReturn(expectedCustomer);
        when(customerService.updateCustomer(1,expectedCustomer)).thenReturn(expectedCustomer);

        boolean actualStatus= creditApplicationService.addCreditApplication("12345678975");
        assertEquals(creditApplication.getCreditLimit(),0.0F);
        assertTrue(actualStatus);
    }

}