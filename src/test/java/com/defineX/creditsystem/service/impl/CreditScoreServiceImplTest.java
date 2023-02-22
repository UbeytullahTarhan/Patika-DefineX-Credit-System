package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.exception.CreditScoreNotValid;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.repository.CreditScoreRepository;
import com.defineX.creditsystem.service.utils.RandomUtilty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceImplTest {

    @Mock
    CreditScoreRepository creditScoreRepository;

    @Mock
    Customer customer;

    @Mock
    RandomUtilty randomUtilty;

    @InjectMocks
    CreditScoreServiceImpl creditScoreService;

    @Test
    void addCreditScore_successful() {
     CreditScore expectedCreditScore=new CreditScore(1,100,null);

     when(creditScoreRepository.save(any())).thenReturn(expectedCreditScore);

     CreditScore actualCreditScore=creditScoreService.addCreditScore(expectedCreditScore);
     assertEquals(expectedCreditScore,actualCreditScore);
    }
    @Test
    void addCreditScore_fail(){
        CreditScore expectedCreditScore=new CreditScore(1,-1,null);

        Exception exception = assertThrows(CreditScoreNotValid.class, () -> {
            creditScoreService.addCreditScore(expectedCreditScore);

        });

        String expectedMessage = " Credit Score can not be negative number !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void calculateCreditScore_successful() {
        CreditScore expectedCreditScore=new CreditScore(2,null,null);

        when(randomUtilty.create0Between2000()).thenReturn(1000);
        when(creditScoreRepository.save(any())).thenReturn(expectedCreditScore);


        CreditScore result = creditScoreService.calculateCreditScore();


        verify(randomUtilty,times(1)).create0Between2000();
        verify(creditScoreRepository, times(1)).save(any());

    }

    @Test
    void getCreditScoreByCustomerIdentityNumber_successful() {
       Customer expectedCustomer= new Customer(1,"123","Ubeytullah","Tarhan",100.0F,"054690",null,null);
       CreditScore expectedCreditScore=new CreditScore(1,100,expectedCustomer);

       when(creditScoreRepository.findCreditScoreByCustomer_IdentityNumber(any())).thenReturn(expectedCreditScore);

      Integer actualCreditScore=creditScoreService.getCreditScoreByCustomerIdentityNumber("123");

      assertEquals(expectedCreditScore.getCreditScore(),actualCreditScore);
    }

    @Test
    void getCreditScoreByCustomerIdentityNumber_not_found() {

        when(creditScoreRepository.findCreditScoreByCustomer_IdentityNumber(any())).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> {
                    Integer actualCreditScore=creditScoreService.getCreditScoreByCustomerIdentityNumber(any());
                }
        );
    }

    @Test
    void calculateCreditScore() {

        CreditScore expectedCreditScore=new CreditScore(2,1000,null);

        when(randomUtilty.create0Between2000()).thenReturn(1000);
        when(creditScoreRepository.save(any())).thenReturn(expectedCreditScore);

        CreditScore resul = creditScoreService.calculateCreditScore();

        verify(creditScoreRepository, times(1)).save(any());
        verify(randomUtilty, times(1)).create0Between2000();
    }
}