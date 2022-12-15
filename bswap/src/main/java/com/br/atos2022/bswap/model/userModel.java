package com.br.atos2022.bswap.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="User")
public class userModel implements UserDetails {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="email",nullable=false, unique=true)
    private String username;//this will be our username in our application

    @Column(nullable = false)
    private String nickname;

    @NotBlank
    @Size(max=255)
    @Column(length = 255, nullable = false)//so the encrypeted password can be stored without lack of space
    private String password;
    
    @JsonIgnoreProperties("users")
    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name="plan_name",nullable = false)
    private planModel currentPlan;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    private List<schedulingModel> schedules = new ArrayList<>();

    @JsonIgnoreProperties("user")//esse cara previni a recursao infinita devido ao onetomany e manytoone
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)//or add the orphanRemoval again //
    private List<evModel> cars = new ArrayList<>();

    
    
    @ManyToMany(fetch = FetchType.EAGER)//cascade =CascadeType.ALL
    @JoinTable(name="user_batteries",
    joinColumns = {@JoinColumn(name="user_id")},
    inverseJoinColumns = {@JoinColumn(name="battery_id")})//@JsonIgnoreProperties(value="users")
    private List<batteryModel>batts=new ArrayList<>();

    // @JsonIgnoreProperties("user")
    // @OneToMany(mappedBy = "user",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    // public List<batteryModel>batts= new ArrayList<>();


    // @JsonIgnoreProperties("users")
    // @ManyToOne(fetch=FetchType.EAGER)
    // @JoinColumn(name="role_id",nullable = false)
    // private roleModel role;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="USERS_ROLES",
        joinColumns=@JoinColumn(name="user_id"),
        inverseJoinColumns=@JoinColumn(name="role_id"))
    private List<roleModel> roles = new ArrayList<>();


    public void addSched(schedulingModel sched){
        schedules.add(sched);
        sched.setUser(this);
    }

    public void removeSched(schedulingModel sched){
        schedules.remove(sched);
        sched.setUser(null);
    }


    public void addRole(roleModel role){
        this.roles.add(role);
    }

    public void addBattery(batteryModel battModel){

        this.batts.add(battModel);
        // battModel.addUser(this);
    }

    public void removeBattery(batteryModel battModel){
        this.batts.remove(battModel);
        // battModel.getUsers().remove(this);
    }


    public void addEV(evModel evModel){
        this.cars.add(evModel);
        evModel.setUser(this);
    }

    public void removeEV(evModel ev){
        this.cars.remove(ev);
        ev.setUser(null);
    }


    

    public userModel(String username, String nickname, @NotBlank @Size(max = 255) String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    


}
