package com.defineX.creditsystem.controller;


import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.CreditScore;
import com.defineX.creditsystem.entity.Customer;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.defineX.creditsystem.exception.handler.GenericExceptionHandler;
import com.defineX.creditsystem.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mvc;

    @Mock
    CustomerServiceImpl customerService;

    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getCustomer() throws Exception {
        //init test values
        List<Customer> expectedCustomers=getTestCustomers();

        //stub-given
        when(customerService.getCustomer(1)).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response=mvc.perform(get("/api/customer/get?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer=new ObjectMapper().readValue(response.getContentAsString(),Customer.class);
        Assert.assertEquals(expectedCustomers.get(0).getIdentityNumber(),actualCustomer.getIdentityNumber());
    }
    @Test
    void addCustomer() throws Exception {
        List<Customer> expectedCustomers=getTestCustomers();
        Customer expectedCustomer=new Customer(1,"17428249782","Ubeytullah","TARHAN",9000.0F,"05703838323",null,null);
        expectedCustomers.add(expectedCustomer);

        ObjectWriter objectWriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr=objectWriter.writeValueAsString(expectedCustomer);
        when(customerService.addCustomer(any())).thenReturn(any());

        MockHttpServletResponse response=mvc.perform(post("/api/customer/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedCustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerService,Mockito.times(1)).addCustomer(any());
    }

    @Test
    void updateCustomer() throws Exception{
        List<Customer> expectedCustomers=getTestCustomers();
        Customer expectedCustomer=new Customer(1,"12345678987","Ubeytullah","TARHAN",9000.0F,"05703838323",null,null);
        Customer updatedCustomer=new Customer(1,"12345678987","Merve","TARHAN",9000.0F,"05703838323",null,null);

        expectedCustomers.add(updatedCustomer);

        ObjectWriter objectWriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr=objectWriter.writeValueAsString(updatedCustomer);
        when(customerService.updateCustomer(any(),any())).thenReturn(updatedCustomer);

        MockHttpServletResponse response=mvc.perform(put("/api/customer/update?id=1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedCustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerService,Mockito.times(1)).updateCustomer(any(),any());

    }

    @Test
    void deleteCustomer() throws Exception {
       when(customerService.deleteCustomer(any())).thenReturn(true);

        MockHttpServletResponse response=mvc.perform(delete("/api/customer/delete?id=5")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr=response.getContentAsString();
        Assert.assertEquals("true",actualResponseStr);
    }




    private List<Customer> getTestCustomers(){


        List<Customer> customers=new ArrayList<>();
        customers.add(new Customer(1,"12345678956","Ubeytullah","Tarhan",5000.0F,"05704567870",
                new CreditScore(1,1000,null),
        Arrays.asList(
                new CreditApplication(1,true,10000.0F,null),
                new CreditApplication(2,false,20000.0F,null)
        )));

        return customers;
    }



}


