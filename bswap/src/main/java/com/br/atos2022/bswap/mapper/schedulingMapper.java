package com.br.atos2022.bswap.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.br.atos2022.bswap.dto.schedulingDTO;
import com.br.atos2022.bswap.model.schedulingModel;

@Mapper(componentModel = "spring")
public interface schedulingMapper {

    @Mapping(target="user",source = "user.nickname")
    @Mapping(target="userID",source = "user.id")
    schedulingDTO toschedulingDTO(schedulingModel sched);

    @Mapping(target="user",source = "user.nickname")
    List<schedulingDTO> toSchedulingDTOs(List<schedulingModel>scheds);

    

}
