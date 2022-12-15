package com.br.atos2022.bswap.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.br.atos2022.bswap.dto.battDTO;
import com.br.atos2022.bswap.model.batteryModel;

@Mapper(componentModel = "spring")
public interface battMapper {

    battDTO tobattDTO(batteryModel batt);

    List<battDTO> tobattDTOs(List<batteryModel>batts);

    batteryModel toBatteryModel(battDTO batt);
    

}
