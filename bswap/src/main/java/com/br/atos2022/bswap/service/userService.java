package com.br.atos2022.bswap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.dto.userDTO;
import com.br.atos2022.bswap.mapper.userMapper;
import com.br.atos2022.bswap.model.userModel;
import com.br.atos2022.bswap.repository.userRepository;

import jakarta.transaction.Transactional;

@Service
public class userService {
    
    @Autowired
    private userRepository userRep;
    
    @Autowired
    private userMapper userMap;

    @Transactional
    public userModel save(userModel roleModel){
        return userRep.save(roleModel);
    }

    public Optional<userModel> findByUsername(String username){
        return userRep.findByUsername(username);
    }

    public Optional<userModel>findById(Integer id){
        return userRep.findById(id);
    }

    public List<userModel> findListByUsername(String username){
        List<userModel> userModelList = new ArrayList<>();
        try{
            userModel userM = userRep.findByUsername(username).orElseThrow(()
            -> new UsernameNotFoundException(username));
        }catch (Exception e){
            return userModelList;//se der erro retorna uma lista vazia
        }
        return userModelList;//do contraito retorna a lista com 1 item dentro!
    }
    public List<userModel>findAll(){
        return userRep.findAll();
    }

    public List<userDTO>findAllDTO(){
        return userMap.toUserDTOs(userRep.findAll());
    }

    


}
