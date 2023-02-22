package com.defineX.creditsystem.controller;

import com.defineX.creditsystem.entity.Customer;
import com.defineX.creditsystem.entity.dto.CustomerDto;
import com.defineX.creditsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/get")
    public ResponseEntity<?> getCustomer(@RequestParam Integer id){
        ResponseEntity<?> response=new ResponseEntity<>(customerService.getCustomer(id),HttpStatus.OK);
        return response ;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customer){
        ResponseEntity<?> response=new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
       return response ;
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteCustomer(@RequestParam Integer id){
       return customerService.deleteCustomer(id);
    }

    @PutMapping("/update")
    public Customer updateCustomer(@Valid @RequestParam Integer id, Customer customer){
       return customerService.updateCustomer(id,customer);
    }
}



