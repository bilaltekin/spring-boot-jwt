package com.tekinbilal.springbootjwt.controller;

import com.tekinbilal.springbootjwt.service.TokenManager;
import com.tekinbilal.springbootjwt.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenManager tokenManager;
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
    {
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                   loginRequest.getPassword()));

           return ResponseEntity.ok(tokenManager.generateToken(loginRequest.getUsername()));
       }
       catch (Exception e)
       {
           throw e;
       }


    }
}
