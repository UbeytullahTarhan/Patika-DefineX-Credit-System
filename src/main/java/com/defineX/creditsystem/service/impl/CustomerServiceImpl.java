package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.dto.CustomerDto;
import com.defineX.creditsystem.entity.mapper.CustomerMapper;
import com.defineX.creditsystem.exception.IdentityNumberAlreadyExistException;
import com.defineX.creditsystem.service.CreditScoreService;
import com.defineX.creditsystem.service.CustomerService;
import com.defineX.creditsystem.exception.NotFoundException;
import com.defineX.creditsystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CreditScoreService creditScoreService;

    @Override
    public Customer getCustomer(Integer id) {
        Optional<Customer> byId= customerRepository.findById(id);
        return byId.orElseThrow(()->new NotFoundException("Customer"));
    }

    @Override
    public Customer addCustomer(CustomerDto customerDto) {
        Customer customer= CustomerMapper.toEntity(customerDto);

        CreditScore creditScore= creditScoreService.calculateCreditScore();

        customer.setCreditScore(creditScore);
        if(customerRepository.findByIdentityNumber(customer.getIdentityNumber())!=null){
          throw  new IdentityNumberAlreadyExistException();
        }
       return customerRepository.save(customer);
    }

    @Override
    public boolean deleteCustomer(Integer id) {
        customerRepository.delete(getCustomer(id));
        return true;
    }

    @Override
    public Customer updateCustomer(Integer id, Customer customer) {
        getCustomer(id);
        customer.setId(id);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomerByIdentityNumber(String identityNumber) {
        Customer customer=customerRepository.findByIdentityNumber(identityNumber);
        if (customer==null){
           throw new NotFoundException("Customer that has this identity number  ");
        }
        return customer;
    }
}
