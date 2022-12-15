package com.br.atos2022.bswap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.atos2022.bswap.model.roleModel;
import com.br.atos2022.bswap.repository.roleRepository;

import jakarta.transaction.Transactional;

@Service
public class roleService {

    @Autowired
    private roleRepository roleRep;

    @Transactional
    public roleModel save(roleModel roleModel){
        return roleRep.save(roleModel);
    }

    public Optional<roleModel> findByRoleID(Integer id){
        return roleRep.findByRoleID(id);
    }
    
}
