package com.defineX.creditsystem.service.impl;

import com.defineX.creditsystem.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Override
    public String sendMessage(String phone, Boolean status, Float limit) {
        String statusTrue="CONSENT";
        String statusFalse="DENIAL";
        String message="";
        if(status==true){
            message= "Your credit application has been approved."+"\n"+
                    "Application details :"+"\n"+
                    "Status :"+statusTrue+"\n"+
                    "Limit :"+limit;
        }else{
            message= "Sorry,your credit application has not been approved."+"\n"+
                    "Application details :"+"\n"+
                    "Status :"+statusFalse+"\n"+
                    "Limit :"+limit;
        }
        System.out.println("A" +"\n"+ "'" +  message + "'"+"\n" + "message was sent to phone number "+ phone);
        return message;
    }
}
