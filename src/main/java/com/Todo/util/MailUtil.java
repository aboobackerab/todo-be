package com.Todo.util;


import com.Todo.DTO.MailDTO;
import com.Todo.Entity.Otp;
import com.Todo.Repo.OtpRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
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

    @Autowired
    private OtpRepo otpRepo;



    public MailDTO createMailDTO(String username, String email, Integer otp) {
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
        message.setHTMLPart("<h3>Dear "+ username+","+ "welcome to TODO!</h3><br />Your One Time Password is  "+"<h2>"+otp+"</h2> <br><br> <i>Note: The username in this mail should be used for signing in.<b>"+username+"</b></i>");

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
        Integer otp = generateRandomSixDigitNumber();
        MailDTO mailDTO = createMailDTO(username, email, otp);
        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth(this.username, this.password);
            HttpEntity<MailDTO> entity = new HttpEntity<>(mailDTO, httpHeaders);

            ResponseEntity<Object> response = restTemplate.exchange(api, HttpMethod.POST, entity, Object.class);
            boolean resp = response.getStatusCode().value() == 200;
            if(resp){
                Otp otpObject = new Otp();
                otpObject.setOtp(otp);
                otpObject.setEmail(email);
                otpObject.setVerified(0);
                otpRepo.save(otpObject);
            }
            return resp;
        }catch (Exception exception){
            log.info("email sending for verification has failed");
            return false;
        }
    }

}
