package com.Todo.Service;

import com.Todo.DTO.UserDataDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<?> addUser(UserDataDTO userDataDTO);

}
