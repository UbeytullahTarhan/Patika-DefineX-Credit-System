package com.defineX.creditsystem.entity.mapper;

import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.dto.CustomerDto;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer){
        CustomerDto customerDto=new CustomerDto();
        customerDto.setIdentityNumber(customer.getIdentityNumber());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setMonthlyIncome(customer.getMonthlyIncome());
        customerDto.setPhone(customer.getPhone());
        return customerDto;
    }

    public static Customer toEntity(CustomerDto customerDto){
        Customer customer=new Customer();
        customer.setIdentityNumber(customerDto.getIdentityNumber());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMonthlyIncome(customerDto.getMonthlyIncome());
        customer.setPhone(customerDto.getPhone());
        return customer;
    }
}
