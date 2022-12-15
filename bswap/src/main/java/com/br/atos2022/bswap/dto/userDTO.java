package com.br.atos2022.bswap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {

    private Integer id;

    private String username;
    
    private String nickname;

    private String currentPlan;

    
}

