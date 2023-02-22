package com.defineX.creditsystem.service.utils;

import org.springframework.stereotype.Component;

@Component
public class RandomUtilty {

    public int create0Between2000(){
        return (int) ((Math.random() * (2000 - 0)) + 0);
    }

}
