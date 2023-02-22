package com.defineX.creditsystem.controller;


import com.defineX.creditsystem.entity.dto.CreditApplicationDto;
import com.defineX.creditsystem.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit_application")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @GetMapping(value = "/get")
    public ResponseEntity<?> getCreditApplication(@Valid @RequestParam String identityNumber) {
        ResponseEntity<?> response=new ResponseEntity<>(creditApplicationService.getCreditApplication(identityNumber),HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCreditApplication(@Valid @RequestBody CreditApplicationDto dto){

        ResponseEntity<?> response;
        try {
            creditApplicationService.addCreditApplication(dto.getIdentityNumber());

            response=new ResponseEntity<>( creditApplicationService.getCreditApplication(dto.getIdentityNumber()),HttpStatus.OK);
        } catch (Exception exception){
            response=new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
