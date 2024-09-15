package com.Todo.Service.Impl;

import com.Todo.DTO.UserDataDTO;
import com.Todo.DTO.VerifyDTO;
import com.Todo.Entity.Otp;
import com.Todo.Entity.UserData;
import com.Todo.Repo.OtpRepo;
import com.Todo.Repo.UserDataRepo;
import com.Todo.Service.UserService;
import com.Todo.exceptions.BadRequestException;
import com.Todo.exceptions.EmailAlreadyExistException;
import com.Todo.exceptions.UserAdditionException;
import com.Todo.exceptions.UserNotExistException;
import com.Todo.util.MailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDataRepo userDataRepo;

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    MailUtil mailUtil;

    @Override
    public ResponseEntity<?> addUser(UserDataDTO userDataDTO) {
        String email = userDataDTO.getEmail();
        Optional<UserData> userDataOPtional = userDataRepo.findByEmail(email);
        if(!userDataOPtional.isEmpty()){
            log.info("email already exist : {}", email);
            throw new EmailAlreadyExistException("Email Already Exist " + email);
        }
        String username = generateUsernameWithUUID(userDataDTO.getFirstName());
        userDataDTO.setUsername(username.toLowerCase());
        userDataDTO.setVerified(0);
        boolean success = mailUtil.sendOTPMail(userDataDTO.getUsername(), userDataDTO.getEmail());
        if(success){
            ObjectMapper objectMapper = new ObjectMapper();
            UserData userData = userDataRepo.save(objectMapper.convertValue(userDataDTO, UserData.class));
            return new ResponseEntity<>(userData, HttpStatus.CREATED);
        }
        throw  new UserAdditionException("Unable to create user please try again later");
    }

    public static String generateUsernameWithUUID(String name) {
        String uuid = UUID.randomUUID().toString();
        return name + "_" + uuid.substring(0, 4);
    }


    @Override
    public ResponseEntity<?> verifyUser(VerifyDTO verifyDTO) {
        Optional<UserData> userDataOptional = userDataRepo.findByEmail(verifyDTO.getEmail());
        if(userDataOptional.isPresent()){
            Optional<Otp> otpOptional = otpRepo.findByEmail(verifyDTO.getEmail());
            if(otpOptional.isPresent()){
                if(otpOptional.get().getOtp() == Integer.parseInt(verifyDTO.getOtp())){
                    Otp otp = otpOptional.get();
                    otp.setVerified(1);
                    UserData userData = userDataOptional.get();
                    userData.setVerified(1);
                    userDataRepo.save(userData);
                    otpRepo.save(otp);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else {
                    throw new BadRequestException(" The Otp you have entered is incorrect");
                }
            }else{
                throw new BadRequestException("Some thing went wrong please try again later");
            }
        }else{
            throw new UserNotExistException("User with email: "+ verifyDTO.getEmail() + " does not exist");
        }
    }
}
