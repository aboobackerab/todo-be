package com.Todo.Controller;

import com.Todo.DTO.AuthRequestDTO;
import com.Todo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO authRequestDTO) throws Exception{
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
        }catch (Exception ex){
            return new ResponseEntity<>("invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(jwtUtil.generateToken(authRequestDTO.getUsername()), HttpStatus.OK);
    }
}
