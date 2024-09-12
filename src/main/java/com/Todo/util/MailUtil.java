package com.Todo.util;


import com.Todo.DTO.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class MailUtil {

    @Value("${mailjet.username}")
    private String username;

    @Value("${mailjet.password}")
    private String password;

    @Value("${mailjet.from}")
    private String from;

    @Value("${mailjet.api}")
    private String api;

    @Autowired
    RestTemplate restTemplate;



    public MailDTO createMailDTO(String username, String email) {
        MailDTO mailjetMessage = new MailDTO();

        MailDTO.Message message = new MailDTO.Message();

        MailDTO.From from = new MailDTO.From();
        from.setEmail(this.from);
        from.setName("Todo App Verification");
        message.setFrom(from);

        MailDTO.To to = new MailDTO.To();
        to.setEmail(email);
        to.setName(username);

        List<MailDTO.To> toList = new ArrayList<>();
        toList.add(to);
        message.setTo(toList);

        message.setSubject("OTP Verification Code");
        message.setTextPart("Greetings from TODO!");
        message.setHTMLPart("<h3>Dear "+ username+","+ "welcome to TODO!</h3><br />Your One Time Password is  "+"<h2>"+generateRandomSixDigitNumber()+"</h2>");

        List<MailDTO.Message> messageList = new ArrayList<>();
        messageList.add(message);

        mailjetMessage.setMessages(messageList);

        return mailjetMessage;
    }

    public static int generateRandomSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public boolean sendOTPMail(String username, String email){
        MailDTO mailDTO = createMailDTO(username, email);
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth(this.username, this.password);
            HttpEntity<MailDTO> entity = new HttpEntity<>(mailDTO, httpHeaders);

            ResponseEntity<Object> response = restTemplate.exchange(api, HttpMethod.POST, entity, Object.class);
            System.out.println(response.getStatusCode().value());
        }catch (Exception exception){
            System.out.println("failed");
            return false;
        }
        return true;
    }

}
