package com.defineX.creditsystem.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @InjectMocks
    MessageServiceImpl messageService;

    @Test
    void sendMessage_consent() {
        String message = "Your credit application has been approved."+"\n"+
                "Application details :"+"\n"+
                "Status :"+"CONSENT"+"\n"+
                "Limit :"+"1000.0";

        String result = messageService.sendMessage("000",true,1000F);

        assertEquals(result,message);
    }
    @Test
    void sendMessage_reject() {
        String message = "Sorry,your credit application has not been approved."+"\n"+
                "Application details :"+"\n"+
                "Status :"+"DENIAL"+"\n"+
                "Limit :"+"0.0";

        String result = messageService.sendMessage("000",false,0F);

        assertEquals(result,message);
    }

}