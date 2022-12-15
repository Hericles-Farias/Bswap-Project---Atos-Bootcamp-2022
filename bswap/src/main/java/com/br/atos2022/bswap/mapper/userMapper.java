package com.br.atos2022.bswap.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.atos2022.bswap.dto.userDTO;
import com.br.atos2022.bswap.model.userModel;

@Mapper(componentModel="spring")
public interface userMapper {
    
    @Mapping(target="currentPlan",source = "currentPlan.name")
    userDTO toUserDTO(userModel user);
    
    @Mapping(target="currentPlan",source = "currentPlan.name")
    List<userDTO>toUserDTOs(List<userModel>users);

}
