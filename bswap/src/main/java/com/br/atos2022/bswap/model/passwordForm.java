package com.br.atos2022.bswap.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class passwordForm {
    
    @NotNull
    private String oldPassword;
    
    @NotNull
    private String newPassword;

    @NotNull
    private String retypedNewPassword;

}
