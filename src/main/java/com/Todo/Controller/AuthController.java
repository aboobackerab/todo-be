package com.Todo.Controller;

import com.Todo.DTO.AuthRequest;
import com.Todo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;


    @PostMapping("/auth")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid username or password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }
}
