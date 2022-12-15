package com.br.atos2022.bswap.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.br.atos2022.bswap.enums.roleName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class roleModel implements GrantedAuthority{

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer roleID;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false, unique=true)
    private roleName RoleName;

    // @JsonIgnoreProperties("role")
    // @OneToMany(mappedBy="role",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    // public List<userModel> users=new ArrayList<>();

    
    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.RoleName.toString();
    }

    


}
