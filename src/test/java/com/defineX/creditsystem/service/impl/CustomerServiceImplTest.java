package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.entity.dto.CustomerDto;
import com.defineX.creditsystem.exception.IdentityNumberAlreadyExistException;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.repository.CustomerRepository;
import com.defineX.creditsystem.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CreditScoreService creditScoreService;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    void getCustomer_successful() {
        //init step
        Customer expectedCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);

        // stub - when step
        Optional<Customer> expectedOptionalCustomer=Optional.of(expectedCustomer);
        when(customerRepository.findById(1)).thenReturn(expectedOptionalCustomer);

        // then step
        Customer actualCustomer=customerService.getCustomer(1);

        // valid step
        assertEquals(expectedCustomer,actualCustomer);

    }
    @Test
    void getCustomer_not_found(){
        //stub - when step
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        //then step
        assertThrows(NotFoundException.class,
                () -> {
                Customer actualCustomer=customerService.getCustomer(1);
                }
                );
    }

    @Test
    void addCustomer_successful() {

        // expected data init
      CustomerDto customerDto=new CustomerDto("12345678","Ubeytullah","TARHAN",7.500F,"05004004040");
      Customer expectedCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);
      CreditScore expectedCreditScore=new CreditScore(1,1000,expectedCustomer);

        // stub - when
        when(creditScoreService.calculateCreditScore()).thenReturn(expectedCreditScore);
        when(customerRepository.findByIdentityNumber(any())).thenReturn(null);
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        //then
      Customer actualCustomer=customerService.addCustomer(customerDto);
      assertEquals(expectedCustomer,actualCustomer);
    }
    @Test
    void addCustomer_fail_customer_already_exist(){
        CustomerDto customerDto=new CustomerDto("12345678","Ubeytullah","TARHAN",7.500F,"05004004040");
        Customer expectedCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);

        when(customerRepository.findByIdentityNumber(any())).thenReturn(expectedCustomer);

        assertThrows(IdentityNumberAlreadyExistException.class,
                () -> {
                    Customer actualCustomer=customerService.addCustomer(customerDto);
                }
        );
    }
    @Test
    void deleteCustomer_successful() {
        Customer expectedCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);

        when(customerRepository.findById(any())).thenReturn(Optional.of(expectedCustomer));
        doNothing().when(customerRepository).delete(any());

        customerService.deleteCustomer(1);
        verify(customerRepository, times(1)).delete(expectedCustomer);
    }
    @Test
    void updateCustomer() {
        Customer actualCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);
        Customer updatedCustomer=new Customer(1,"12345678","Mehmet","TARHAN",7.500F,"05004004040",null,null);

        assertNotEquals(actualCustomer,updatedCustomer);
    }

    @Test
    void getCustomerByIdentityNumber_successful() {
        Customer expectedCustomer=new Customer(1,"12345678","Ubeytullah","TARHAN",7.500F,"05004004040",null,null);

        when(customerRepository.findByIdentityNumber("12345678")).thenReturn(expectedCustomer);

        Customer actualCustomer=customerService.getCustomerByIdentityNumber("12345678");

        assertEquals(expectedCustomer,actualCustomer);
    }
    @Test
    void getCustomerByIdentityNumber_identity_number_not_found(){
        when(customerRepository.findByIdentityNumber(any())).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> {
                    Customer actualCustomer=customerService.getCustomerByIdentityNumber("12345678");
                }
        );

    }

}