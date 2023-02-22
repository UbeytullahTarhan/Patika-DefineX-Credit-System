package com.defineX.creditsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {


    @Column(unique = true)
    @NotBlank(message = "Identity Number can not be null!")
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}[02468]{1}$",message = "Identity Number is not valid!")
    private String identityNumber;

    @NotBlank(message = "First Name can not be null!")
    private String firstName;

    @NotBlank(message = "Last Name can not be null!")
    private String lastName;

  //  @NotBlank(message = "Monthly Income can not be null!")
    private Float monthlyIncome;

    @NotBlank(message = "Phone can not be null!")
    private String phone;
}
