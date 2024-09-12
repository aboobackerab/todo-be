package com.Todo.Service.Impl;

import com.Todo.DTO.UserDataDTO;
import com.Todo.Entity.UserData;
import com.Todo.Repo.UserDataRepo;
import com.Todo.Service.UserService;
import com.Todo.exceptions.EmailAlreadyExistException;
import com.Todo.util.MailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        ObjectMapper objectMapper = new ObjectMapper();
        UserData userdata = objectMapper.convertValue(userDataDTO, UserData.class);
        if(null != userdata){
            boolean success = mailUtil.sendOTPMail(userDataDTO.getUsername(), userDataDTO.getEmail());
            System.out.println(success);
        }
        return new ResponseEntity<>(userdata, HttpStatus.OK);
    }
}
