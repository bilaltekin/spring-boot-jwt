package com.tekinbilal.springbootjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyUserDetailsService implements UserDetailsService {
   private Map<String,String> users = new HashMap<>();
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @PostConstruct
   public void init()
   {
       users.put("ahmet",passwordEncoder.encode( "123"));
   }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

       if (users.containsKey(userName))
       return new User(userName,users.get(userName),new ArrayList<>());

       throw new UsernameNotFoundException(userName);
    }
}
