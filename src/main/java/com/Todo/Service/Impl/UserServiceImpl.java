package com.Todo.Service.Impl;

import com.Todo.DTO.UserDataDTO;
import com.Todo.Entity.UserData;
import com.Todo.Repo.UserDataRepo;
import com.Todo.Service.UserService;
import com.Todo.exceptions.EmailAlreadyExistException;
import com.Todo.exceptions.UserAdditionException;
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
    MailUtil mailUtil;

    @Override
    public ResponseEntity<?> addUser(UserDataDTO userDataDTO) {
        String email = userDataDTO.getEmail();
        Optional<UserData> userDataOPtional = userDataRepo.findByEmail(email);
        if(!userDataOPtional.isEmpty()){
            log.info("email already exist : {}", email);
            throw new EmailAlreadyExistException("Email Already Exist" + email);
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
}
