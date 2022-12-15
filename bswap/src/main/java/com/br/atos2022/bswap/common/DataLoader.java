package com.br.atos2022.bswap.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.atos2022.bswap.enums.roleName;
import com.br.atos2022.bswap.model.batteryModel;
import com.br.atos2022.bswap.model.evModel;
import com.br.atos2022.bswap.model.planModel;
import com.br.atos2022.bswap.model.roleModel;
import com.br.atos2022.bswap.model.userModel;
import com.br.atos2022.bswap.repository.planRepository;
import com.br.atos2022.bswap.repository.roleRepository;
import com.br.atos2022.bswap.repository.userRepository;
import com.br.atos2022.bswap.service.batteryService;
import com.br.atos2022.bswap.service.planService;
import com.br.atos2022.bswap.service.roleService;
import com.br.atos2022.bswap.service.userService;

@Component
public class DataLoader implements ApplicationRunner {
    
    @Autowired
    private  planService planServ;

    @Autowired
    private batteryService battServ;

    @Autowired
    private roleService roleServ;

    @Autowired
    private userService userServ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(ApplicationArguments args){
        ////criando os planos logo de cara
        // planModel bronze = new planModel();
        // bronze.setName("BRONZE");
        // bronze.setEnergyPrice(21);
        // bronze.setFixedCost(7);
        // bronze.setRenewPeriod("Monthly");
        // planServ.save(bronze);
        // planModel silver = new planModel();
        // //second basic plan
        // silver.setName("SILVER");
        // silver.setEnergyPrice(17);
        // silver.setFixedCost(12);
        // silver.setRenewPeriod("Semesterly");
        // planServ.save(silver);
        // //third basic plan
        // planModel gold = new planModel();
        // gold.setName("GOLD");
        // gold.setEnergyPrice(12);
        // gold.setFixedCost(15);
        // gold.setRenewPeriod("Annualy");
        // planServ.save(gold);
        // //special plan - Only admins and some specific people can use it
        // planModel special = new planModel();
        // special.setName("SPECIAL");
        // special.setEnergyPrice(5);
        // special.setFixedCost(5);
        // special.setRenewPeriod("Annualy");
        // planServ.save(special);
        

        // ////cria as roles logo de cara
        // ////FOR THE ADMIN
        // roleModel roleAdmin = new roleModel();
        // roleAdmin.setRoleName(roleName.ROLE_ADMIN);
        // roleServ.save(roleAdmin);
        // //AND FOR THE USER
        // roleModel roleUser = new roleModel();
        // roleUser.setRoleName(roleName.ROLE_USER);
        // roleServ.save(roleUser);
        // ////criando o admin logo de cara
        // userModel userAdmin = new userModel();
        // userAdmin.setNickname("Hericles");
        // userAdmin.setPassword(passwordEncoder.encode("senha000"));
        // userAdmin.setUsername("hericles@gmail.com");
        // userAdmin.setCurrentPlan(special);
        // userAdmin.addRole(roleAdmin);
        // userServ.save(userAdmin);
        // // //e um user qualquer

        // userModel userUser = new userModel();
        // userUser.setNickname("Juliana");
        // userUser.setPassword(passwordEncoder.encode("senha123"));
        // userUser.setUsername("juliana@gmail.com");
        // userUser.setCurrentPlan(bronze);
        // userUser.addRole(roleUser);
        // userServ.save(userUser);
        // //DEFAULT LIST OF SUPPORTED EVS
        // // evModel ev = new evModel();
        // // ev.setBrand("BMW");
        // // ev.setModel("I3");
        // // ev.setPlate("AXS011");

        // ////DEFAULT LIST OF SUPPROTED BATTERIES
        // batteryModel batt = new batteryModel();
        // batt.setBatteryCapacity(30);
        // batt.setType("LiFePO4");
        // battServ.save(batt);

        // batteryModel batt2 = new batteryModel();
        // batt2.setBatteryCapacity(40);
        // batt2.setType("NCM");
        // battServ.save(batt2);

        // batteryModel batt3 = new batteryModel();
        // batt3.setBatteryCapacity(10);
        // batt3.setType("LTO");
        // battServ.save(batt3);

        // batteryModel batt4 = new batteryModel();
        // batt4.setBatteryCapacity(40);
        // batt4.setType("LiFePO4");
        // battServ.save(batt4);

        // batteryModel batt5 = new batteryModel();
        // batt5.setBatteryCapacity(10);
        // batt5.setType("NCM");
        // battServ.save(batt5);

        // batteryModel batt6 = new batteryModel();
        // batt6.setBatteryCapacity(15);
        // batt6.setType("NCM");
        // battServ.save(batt6);

        // batteryModel batt7 = new batteryModel();
        // batt7.setBatteryCapacity(15);
        // batt7.setType("LTO");
        // battServ.save(batt7);


    }

    
}
