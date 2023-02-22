package com.defineX.creditsystem.service;

import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.dto.CustomerDto;

public interface CustomerService {

    Customer getCustomer(Integer id);

    Customer addCustomer(CustomerDto customer);

    boolean deleteCustomer(Integer id);

    Customer updateCustomer(Integer id, Customer customer);

    Customer getCustomerByIdentityNumber(String identityNumber);
}
