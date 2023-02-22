package com.defineX.creditsystem.controller;

import com.defineX.creditsystem.entity.CreditApplication;
import com.defineX.creditsystem.entity.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.defineX.creditsystem.exception.handler.GenericExceptionHandler;
import com.defineX.creditsystem.service.impl.CreditApplicationServiceImpl;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditApplicationControllerTest {

    private MockMvc mvc;

    @Mock
    CreditApplicationServiceImpl creditApplicationService;

    @InjectMocks
    CreditApplicationController creditApplicationController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(creditApplicationController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getCreditApplication() throws Exception{
        List<CreditApplication> expectedCreditApplications=getTestCreditApplications();

        when(creditApplicationService.getCreditApplication("12345678987")).thenReturn(expectedCreditApplications);

        MockHttpServletResponse response=mvc.perform(get("/api/credit_application/get?identityNumber=12345678987")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CreditApplication> actualCreditApplications=new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CreditApplication>>() {
        });
        assertEquals(expectedCreditApplications.size(),actualCreditApplications.size());
    }

    @Test
    void addCreditApplication() throws Exception{
        List<CreditApplication> expectedCreditApplications=getTestCreditApplications();
        CreditApplication expectedCreditApplication=new CreditApplication();
        expectedCreditApplication.setCreditLimit(20000.0F);
        expectedCreditApplications.add(expectedCreditApplication);

        ObjectWriter objectWriter=new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCreditApplicationJsonStr=objectWriter.writeValueAsString(expectedCreditApplication);

        MockHttpServletResponse response=mvc.perform(post("/api/credit_application/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedCreditApplicationJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(creditApplicationService,Mockito.times(1)).addCreditApplication(any());

    }
    private List<CreditApplication> getTestCreditApplications(){
        List<CreditApplication> creditApplications=new ArrayList<>();
        creditApplications.add(new CreditApplication(1,true,1000.0F,new Customer(1,"12345678987","Ubeytullah","TARHAN",10000.0F,"05708975647",null,null)));
        creditApplications.add(new CreditApplication(2,false,0.0F,new Customer(2,"12345678987","Kaya","TARHAN",8000.0F,"05708975634",null,null)));
        return creditApplications;
    }
}