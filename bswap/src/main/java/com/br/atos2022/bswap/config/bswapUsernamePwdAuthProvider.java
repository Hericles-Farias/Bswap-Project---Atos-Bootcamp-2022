package com.br.atos2022.bswap.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.atos2022.bswap.model.userModel;
import com.br.atos2022.bswap.service.userService;

// @Component
// public class bswapUsernamePwdAuthProvider implements AuthenticationProvider {
    
//     @Autowired
//     private userService userServ;

//     @Autowired
//     private PasswordEncoder passwordEncoder;
    

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         String username = authentication.getName();
//         String pwd = authentication.getCredentials().toString();
//         List<userModel> user = userServ.findListByUsername(username);
//         if (user.size()>0){
//             if(passwordEncoder.matches(pwd, user.get(0).getPassword())){
//                 List<GrantedAuthority>authorities=new ArrayList<>();
//                 authorities.add(new SimpleGrantedAuthority(user.get(0).getRoles().get(0).toString()));
//                 return new UsernamePasswordAuthenticationToken(username,pwd, authorities);
//             }else{
//                 throw new BadCredentialsException("Invalid Password");
//             }
//         }else{
//             throw new BadCredentialsException("No user registered with this details!");
//         } 

//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//     }



// }
