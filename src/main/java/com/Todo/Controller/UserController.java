package com.Todo.Controller;


import com.Todo.DTO.UserDataDTO;
import com.Todo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/add")
    public ResponseEntity<?> adduser(@RequestBody UserDataDTO userDataDTO){
        log.info("Request received for adding user with mail : {}", userDataDTO.getEmail());
        return userService.addUser(userDataDTO);
    }

}
