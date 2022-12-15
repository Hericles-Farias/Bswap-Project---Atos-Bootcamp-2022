package com.br.atos2022.bswap.controller;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.atos2022.bswap.service.planService;
import com.br.atos2022.bswap.service.roleService;
import com.br.atos2022.bswap.service.userService;

import lombok.extern.slf4j.Slf4j;

import com.br.atos2022.bswap.Exceptions.PlanNotFoundException;
import com.br.atos2022.bswap.model.planModel;
import com.br.atos2022.bswap.model.roleModel;
import com.br.atos2022.bswap.model.userModel;

@Controller
@Slf4j
@RequestMapping("/bswap")
public class credentialsController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private userService userServ;

    @Autowired
    private planService planServ;

    @Autowired
    private roleService roleServ;


    @ModelAttribute("userModel")
    public userModel toUserModel(){
        return new userModel();
    }


    @PostMapping("/register")//public ResponseEntity<String>registerUser(@RequestBody @ModelAttribute("userModel") userModel userModel){
    public String registerUser(@RequestBody @ModelAttribute("userModel") userModel userModel){
        log.info("At Registration Form");
        userModel savedUser=null;
        //create it here - Every new user it is added with a Bronze account
        String planName= "BRONZE";
        planModel bronze = planServ.findByName(planName).orElseThrow(()-> new PlanNotFoundException(planName));
        userModel.setCurrentPlan(bronze);
        //1 is the default for ROLE_ADMIN
        Integer roleID = 2;//2 is the default for the ROLE_USER
        roleModel roleUser = roleServ.findByRoleID(roleID).orElseThrow(()-> new com.br.atos2022.bswap.Exceptions.RoleNotFoundException(roleID));
        userModel.addRole(roleUser);
        ResponseEntity response = null;
        try{
            String hashPwd = passwordEncoder.encode(userModel.getPassword());
            userModel.setPassword(hashPwd);
            
            System.out.println(hashPwd.length());

            savedUser=userServ.save(userModel);
            if (savedUser.getId()>0){
                //response=ResponseEntity.status(HttpStatus.CREATED)
                //.body("Given user details are successfully registered!");
                log.info("Registration succeded!");
                return "redirect:/bswap/login";
            }
        }catch (Exception e){
            //response= ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred due to: "+e.getMessage());
            log.warn("Registration failed!");
            return "redirect:/bswap/register?error";
        }
        return "redirect:/bswap/login";
    }


	@GetMapping("/login")
	public String login() {
        log.info("At Login Page!");
		return "login";
    }

	@GetMapping("/register")
	public String register() {
        log.info("At Registration Page!");
		return "register";
    }

	// @GetMapping("/userhomepage")
	// public String userhomepage() {
    //     log.info("At user home page!");
	// 	return "userhomepage";
    // }

    // @GetMapping("/adminhomepage")
	// public String adminhomepage() {
    //     log.info("At admin home page!");
	// 	return "adminhomepage";
    // }


    @GetMapping("/Forbiden")
	public String Forbiden() {
        log.info("At Forbiden Page!");
		return "403";
    }

    
}
