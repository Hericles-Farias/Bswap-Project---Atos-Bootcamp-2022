package com.br.atos2022.bswap.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.br.atos2022.bswap.dto.planDTO;
import com.br.atos2022.bswap.model.planModel;

@Mapper(componentModel="spring")
public interface planMapper {
    
    planDTO toPlanDTO(planModel plan);
    List<planDTO>toPlanDTOs(List<planModel>plans);

}
