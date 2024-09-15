package com.Todo.Service;

import com.Todo.DTO.UserDataDTO;
import com.Todo.DTO.VerifyDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> addUser(UserDataDTO userDataDTO);

    ResponseEntity<?> verifyUser(VerifyDTO verifyDTO);
}
