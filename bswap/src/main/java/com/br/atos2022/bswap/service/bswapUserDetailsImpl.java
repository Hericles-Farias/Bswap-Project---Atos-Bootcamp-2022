package com.br.atos2022.bswap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.model.userModel;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class bswapUserDetailsImpl implements UserDetailsService {
    
    @Autowired
    private userService userServ;
    //converter o user model em user details que é o tipo esperado pelo spring security!
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        userModel user = userServ.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: "+username));
            
        return new User(user.getUsername(),user.getPassword(),true,true,true,true,user.getAuthorities());
        //return user;
    }


}
