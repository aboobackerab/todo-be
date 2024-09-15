package com.Todo.Controller;

import com.Todo.DTO.AuthRequestDTO;
import com.Todo.DTO.VerifyDTO;
import com.Todo.Service.UserService;
import com.Todo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;


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

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyDTO verifyDTO){
        log.info("verifying otp of user :{}", verifyDTO.getEmail());
        return userService.verifyUser(verifyDTO);
    }
}
