package com.br.atos2022.bswap.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.servlet.ModelAndView;

import com.br.atos2022.bswap.Exceptions.BatteryNotFoundException;
import com.br.atos2022.bswap.Exceptions.EVNotFoundException;
import com.br.atos2022.bswap.Exceptions.PlanNotFoundException;
import com.br.atos2022.bswap.Exceptions.SchedNotFoundException;
import com.br.atos2022.bswap.Exceptions.UserNotFoundException;
import com.br.atos2022.bswap.model.EVForm;
import com.br.atos2022.bswap.model.batteryModel;
import com.br.atos2022.bswap.model.dayAhead;
import com.br.atos2022.bswap.model.evModel;
import com.br.atos2022.bswap.model.passwordForm;
import com.br.atos2022.bswap.model.planModel;
import com.br.atos2022.bswap.model.schedForm;
import com.br.atos2022.bswap.model.schedulingModel;
import com.br.atos2022.bswap.model.userModel;
import com.br.atos2022.bswap.repository.evRepository;
import com.br.atos2022.bswap.service.batteryService;
import com.br.atos2022.bswap.service.evService;
import com.br.atos2022.bswap.service.planService;
import com.br.atos2022.bswap.service.schedulingService;
import com.br.atos2022.bswap.service.userService;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import ch.qos.logback.core.model.Model;

import com.br.atos2022.bswap.common.dateHandler;
import com.br.atos2022.bswap.dto.battDTO;
import com.br.atos2022.bswap.dto.planDTO;
import com.br.atos2022.bswap.dto.schedSimp;
import com.br.atos2022.bswap.dto.schedulingDTO;
import com.br.atos2022.bswap.dto.userDTO;
import com.br.atos2022.bswap.mapper.schedulingMapper;
import com.br.atos2022.bswap.mapper.userMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/bswap")
public class userController {
    
    @Autowired
    private userService userServ;

    @Autowired
    private schedulingMapper schedMap;

    @Autowired
    private schedulingService schedServ;

    @Autowired
    private batteryService battServ;

    @Autowired
    private planService planServ;


    @Autowired
    private evService evServ;

    //USER RELATED
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}")
    public ModelAndView showUserProfile(@PathVariable @Valid Integer id){
        log.info("At user Profile page!");
        // In this endpoint we should show the user related info and a link to make changes
        // We should also assign a single link for password changings
        // Here we display all relevant information about the user!
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }
        ModelAndView mv = new ModelAndView("/userRelated/myProfile");
        if(user.getId().equals(id) || auth.getAuthorities().stream().anyMatch(a->a
        .getAuthority().equals("ROLE_ADMIN"))){
            if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                if(!user.getId().equals(id)){
                    user = userServ.findById(id).orElseThrow(()->new UserNotFoundException(id));
                    //IF IT ENTERS HERE IT MEANS THE ADMIN IS TRYING TO SEE/SET INFO 
                    //FROM ANOTHER USER, THEN WE NEED TO CHANGE THE CURRENT USER TO THE
                    //RIGHT ONE (IN THIS CASE THE REAL TARGET IS THE USER THE ADMIN WANTS TO SEE NOT HIMSELF)
                    
                    //mv.addObject("user", user);
                    //return mv;
                }
            }
            // ALL THE LOGIC YOU NEED GOES HERE, IT WONT ENTER IF THE USER DOES NOT HAVE ANY
            // PERMISSION AND THE ERROR 403 WILL BE THROWN
            mv.addObject("user", user);
            mv.addObject("homePage",homePage);
            return mv;
        }else{
            mv = new ModelAndView("error-403");
            log.warn("Forbiden Page!");
            return mv;
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/userHomePage")
    public ModelAndView userHomePage(){
        log.info("at user home page!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        ModelAndView mv = new ModelAndView("userHomePage");
    
        mv.addObject("user", user);
        return mv;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adminHomePage")
    public ModelAndView adminHomePage(){
        log.info("At admin home page!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        ModelAndView mv = new ModelAndView("adminHomePage");
        mv.addObject("user", user);
        return mv;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/showPasswordForm")
    public ModelAndView showPasswordForm(@PathVariable Integer id){
        log.info("At Change Password Form!");
        //authenticate user!
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }
        ModelAndView mv = new ModelAndView("/userRelated/changePassword");
        mv.addObject("homePage", homePage);
        //ModelAndView mv = new ModelAndView("/userRelated/changePassword");

        //mv.addObject("passwordForm", );
        return mv;
    }

    @ModelAttribute("passwordForm")
    public passwordForm toPasswordForm(){
        return new passwordForm();
    }
    @ModelAttribute("evModel")
    public evModel toEVModel(){
        return new evModel();
    }
    @ModelAttribute("EVForm")
    public EVForm toEVForm(){
        return new EVForm();
    }
    
    @ModelAttribute("battDTO")
    public battDTO tobattDTO(){
        return new battDTO();
    }

    @ModelAttribute("batteryModel")
    public batteryModel tobatteryModel(){
        return new batteryModel();
    }
    @ModelAttribute("planDTO")
    public planDTO toplanDTO(){
        return new planDTO();
    }

    @ModelAttribute("planModel")
    public planModel toplanModel(){
        return new planModel();
    }

    @ModelAttribute("dayAhead")
    public dayAhead toDayAhead(){
        return new dayAhead();
    }
    @ModelAttribute("schedSimp")
    public schedSimp toSchedSimp(){
        return new schedSimp();
    }
    @ModelAttribute("schedForm")
    public schedForm toschedForm(){
        return new schedForm();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/myProfile/{id}/changePassword")
    public String changeUserPassword(@RequestBody @Valid @ModelAttribute("passwordForm") passwordForm passwordForm,@PathVariable @Valid Integer id){
        log.info("Changing Password...!");
        //authenticate user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
                
        //ABOVE GOES THE AUTHENTICATION PROCEDURE
        if(user.getId().equals(id) || auth.getAuthorities().stream().anyMatch(a->a
        .getAuthority().equals("ROLE_ADMIN"))){
            if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                if(!user.getId().equals(id)){
                    user = userServ.findById(id).orElseThrow(()->new UserNotFoundException(id));
                    //IF IT ENTERS HERE IT MEANS THE ADMIN IS TRYING TO SEE/SET INFO 
                    //FROM ANOTHER USER, THEN WE NEED TO CHANGE THE CURRENT USER TO THE
                    //RIGHT ONE (IN THIS CASE THE REAL TARGET IS THE USER THE ADMIN WANTS TO SEE NOT HIMSELF)
                    
                }
            }
            // ALL THE LOGIC YOU NEED GOES HERE, IT WONT ENTER IF THE USER DOES NOT HAVE ANY
            // PERMISSION AND THE ERROR 403 WILL BE THROWN
            //password changing steps
            String oldPassword = user.getPassword();
            BCryptPasswordEncoder b = new BCryptPasswordEncoder();
            //System.out.println(b.matches(passwordForm.getOldPassword(),oldPassword));
            if(!b.matches(passwordForm.getOldPassword(),oldPassword)){
                log.warn("Could not change the password: Wrong password!");
                return "redirect:/bswap/myProfile/"+id+"/showPasswordForm?wrongpwd";
            }
            else if (!passwordForm.getNewPassword().equals(passwordForm.getRetypedNewPassword())){
                log.warn("Could not change the password: Provided passwords are different!");
                return "redirect:/bswap/myProfile/"+id+"/showPasswordForm?pwddiff";
            }        
            else if (b.matches(passwordForm.getNewPassword(),oldPassword)){
                log.warn("Could not change the password: New password is equal to the old one!");
                return "redirect:/bswap/myProfile/"+id+"/showPasswordForm?pwdequal";
            }
            //ModelAndView mv = new ModelAndView("changePasswordForm");
            String hashP = b.encode(passwordForm.getNewPassword());
            user.setPassword(hashP);
            userServ.save(user);
            //password changing steps
            return "redirect:/bswap/myProfile/"+id+"/showPasswordForm?success";
            
        }else{
        
            return "redirect:/error";
        }
    
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/removeEV/{plate}")
    public String removeEVFromUser(@PathVariable @Valid Integer id, @PathVariable @Valid String plate){
        log.info("Removing EV from user...");
        //System.out.println(id);
        //System.out.println(plate);
        //authenticate user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        evModel ev = evServ.findByPlate(plate).orElseThrow(
            ()-> new EVNotFoundException(plate)
        );
        user.removeEV(ev);
        userServ.save(user);
        log.info("Removing EV successfully removed!");
        return "redirect:/bswap/myProfile/"+id+"?evDeleted";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/removeBattery/{battID}")
    public String removeBattFromUser(@PathVariable @Valid Integer id, @PathVariable @Valid Integer battID){
        log.info("Removing Battery from user...");
        //System.out.println(id);
        //System.out.println(plate);
        //authenticate user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        batteryModel batt = battServ.findByBattID(battID).orElseThrow(
            ()-> new BatteryNotFoundException(battID)
        );

        user.removeBattery(batt);
        userServ.save(user);
        log.info("Battery successfully removed from user!");
        return "redirect:/bswap/myProfile/"+id+"?battDeleted";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/showPlanSelection")
    public ModelAndView showPlanSelection(@PathVariable @Valid Integer id){
        log.info("At Plans Page!");
        String homePage;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }
        ModelAndView mv = new ModelAndView("userRelated/planSelection");
        mv.addObject("homePage",homePage);
        return mv;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/changeToPlan/{name}")
    public String changeUserPlan(@PathVariable @Valid Integer id,@PathVariable @Valid String name){
        log.info("Changing User Plan...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        try{
            planModel plan = planServ.findByName(name).orElseThrow(
                ()-> new PlanNotFoundException(name));
            user.setCurrentPlan(plan);
            userServ.save(user);
            log.info("Plan successfully changed!");
            return "redirect:/bswap/myProfile/"+id;
        }catch(Exception e){
            log.info("Could not change plan!");
            return "redirect:/bswap/myProfile/"+id+"/showPlanSelection?planNotChanged";
        }

        
    }


    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myProfile/{id}/showEVForm")
    public ModelAndView showAddEVForm(@PathVariable @Valid Integer id){
        log.info("Adding EV to user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }
        //Retrieve all supported batteries from bank
        List<String>batts = battServ.getAllBattSepcs();
        batts.add(0, "N/A");
        //and get all batteries inside this user
        List<batteryModel>userBatts=user.getBatts();
        if(userBatts.size()>0){
            //convert them to typecap string
            for(batteryModel b: userBatts){
                batts.remove(b.toTypeCap());
            }
            //and remove each one from the string list
            //we are going to pass to the frontend via thymeleaf
        }
        //then remove the ones the user alredy have
        

        ModelAndView mv = new ModelAndView("userRelated/evForm");
        mv.addObject("batts", batts);
        mv.addObject("homePage", homePage);
        return mv;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/myProfile/{id}/addEV")
    public String addEV(@RequestBody @Valid @ModelAttribute("EVForm") EVForm EVForm, @PathVariable @Valid Integer id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        ////Create the EV object
        System.out.println(EVForm.getPlate());
        System.out.println(EVForm.getBrand());
        System.out.println(EVForm.getModel());
        System.out.println(EVForm.getBattSpec());
        System.out.println(!EVForm.getBattSpec().equals("N/A"));
        System.out.println(EVForm.getBattSpec());
        batteryModel batt=new batteryModel();
        if(!EVForm.getBattSpec().equals("N/A")){
            // this part here could be done in just 2 lines of code or even less, but for now
            // let it be!
            List<String>lista=new ArrayList<>();
            String[] arrOfStr = EVForm.getBattSpec().split(" - ",2);
            String type;
            Integer cap;
            for (String st: arrOfStr){
                lista.add(st);
            }
            type=lista.get(0);
            String[] cap2=lista.get(1).split(" ",2);
            for (String st: cap2){
                lista.add(st);
            }
            cap=Integer.parseInt(lista.get(2));
            System.out.println(type);
            System.out.println(cap);
            batt = battServ.findByTypeAndBatteryCapacity(type, cap).get(0);
            System.out.println(batt);
        }else{
            log.info("Battery not provided");
        }
        try{
            evModel ev = new evModel();
            ev.setPlate(EVForm.getPlate());
            ev.setBrand(EVForm.getBrand());
            ev.setModel(EVForm.getModel());
            user.addEV(ev);
            if(!EVForm.getBattSpec().equals("N/A")){
                // batt.addUser(user);
                // battServ.save(batt);
                user.addBattery(batt);
                log.info("Battery added to user!");
            }
            userServ.save(user);
            log.info("EV added to user!");
            return "redirect:/bswap/myProfile/"+id+"/showEVForm?EVandBattsuccess";
        }catch (Exception e){
            log.warn("Could not add EV to user!");
            return "redirect:/bswap/myProfile/"+id+"/showEVForm?EVsuccess";
        }

            //user.addBattery(batt);
            //userServ.save(user);
            //return "redirect:/bswap/myProfile/"+id+"/showEVForm?EVandBattsuccess";
            //log.info("EV added to user!");
        //     return "redirect:/bswap/myProfile/"+id+"/showEVForm?EVandBattsuccess";
        // }else{
        //     log.warn("Could not add EV to user!");
        //     return "redirect:/bswap/myProfile/"+id+"/showEVForm?EVsuccess";
        // }
        
    }
    

    //Scheduling Related
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/mySchedulings/{id}/showSchedForm")
    public ModelAndView showSchedForm(@PathVariable @Valid Integer id){
        log.info("At Scheduling Form");
        //first generated all periods for tomorrow from 7h to 21h with dt=10min!
        //first generated all periods for tomorrow from 7h to 21h with dt=10min!
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }

        ModelAndView mv = new ModelAndView("userRelated/schedForm");
        mv.addObject("homePage", homePage);
        //get all the supported batteries!
        List<String>allBatts=battServ.getAllBattSepcs();
        mv.addObject("allBatts", allBatts);

        dateHandler dh = new dateHandler();
        List<String> datesIni = dh.allDatesWithinRange();
        String startDate = datesIni.get(0);
        String endDate = datesIni.get(datesIni.size()-1);
        //no we need to check if one of the dates inside this list was already taken from
        //another user, if yes we then need to remove the entry from the datesIni list!
        
        List<String>numbers=new ArrayList<>();
        numbers.add("sete");
        numbers.add("oito");
        numbers.add("nove");
        numbers.add("dez");
        numbers.add("onze");
        numbers.add("doze");
        numbers.add("treze");
        numbers.add("quatorze");
        numbers.add("quinze");
        numbers.add("dezesseis");
        numbers.add("dezesete");
        numbers.add("dezoito");
        numbers.add("dezenove");
        numbers.add("vinte");
        numbers.add("vinteum");
        numbers.add("vintedois");
        

        List<schedulingDTO>scheds=schedServ.findAllBetweenRangeDTO(startDate,endDate);
        ///List<schedSimp>schedS=new ArrayList<>();
        //String sStr;
        List<schedSimp>schedTemp=new ArrayList<>();
        schedSimp sx;
        for (var j=0;j<15;j++){
            schedTemp=new ArrayList<>();
            for (var i=j*6;i<j*6+6;i++){
                    
                //for(String sk : datesIni){
                    String sk = datesIni.get(i);
                    //System.out.println(sk);
                    //schedS.add(new schedSimp(sk,"On"));
                    sx = new schedSimp(sk,"On");
                    for(schedulingDTO s:scheds){
                        
                        if(!s.Status.equals("CANCELLED")){
                            //System.out.println(s.getDateFormated()+"-"+sk);
                            if(s.getDateFormated().equals(sk)){
                                //System.out.println("enter hre");
                    
                            sx.setStatus("Off");
                                }

                            } 
                        }
                    if(sx.getStatus().equals("On")){
                        schedTemp.add(sx);
                    }
                    
                    //}
            }
            mv.addObject(numbers.get(j), schedTemp);
        }
        return mv;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/mySchedulings/{id}/addScheduling")
    public String addScheduling(@RequestBody @Valid @ModelAttribute("schedForm") schedForm schedF, @PathVariable Integer id){
        log.info("Adding new scheduling to user...");
        System.out.println(schedF.getDate());
        System.out.println(schedF.getBattSpec());


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        schedulingModel  schedM = new schedulingModel();
        schedM.setBattSpec(schedF.getBattSpec());
        schedM.setStatus("CONFIRMED");
        Timestamp timestamp;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date parsedDate = dateFormat.parse(schedF.getDate());
            timestamp = new Timestamp(parsedDate.getTime());
            schedM.setDate(timestamp);
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption 
            e.printStackTrace();
        }
        
        

        user.addSched(schedM);
        schedServ.save(schedM);
        userServ.save(user);    
        log.info("Scheduling created!");
        return "redirect:/bswap/mySchedulings/"+id+"/showSchedForm" +"?success";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/mySchedulings/{id}")
    public ModelAndView showScheds(@PathVariable Integer id){
        log.info("Retrieving all schedulings from user...");
        ModelAndView mv = new ModelAndView("userRelated/myScheds");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        //ABOVE GOES THE AUTHENTICATION PROCEDURE
        if(user.getId().equals(id) || auth.getAuthorities().stream().anyMatch(a->a
        .getAuthority().equals("ROLE_ADMIN"))){
            if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                if(!user.getId().equals(id)){
                    user = userServ.findById(id).orElseThrow(()->new UserNotFoundException(id));
                }
            }
            String homePage;
            if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
                homePage="adminHomePage";
            }else{
                homePage="userHomePage";
            }
            List<schedulingDTO>scheds = schedMap.toSchedulingDTOs(user.getSchedules());
            mv.addObject("scheds", scheds);
            mv.addObject("homePage", homePage);
            log.info("Schedulings retrieved!");
            return mv;
            
        }else{
            mv = new ModelAndView("error-403");
            log.info("Forbidden Page!");
            return mv;
        }

    }

    //This is what the BSS admin sees!
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/userSchedulings")
    public ModelAndView showUserScheds(){
        log.info("Retrieving all schedulings!");
        //como esta endpoint nao recebeu o parametro id na requestbody, a MV que estamos retornando
        //usa esta info, entao temos que passa-la
        ModelAndView mv = new ModelAndView("userRelated/userScheds");
        List<schedulingDTO>scheds = schedServ.findAllDTO();
        mv.addObject("scheds", scheds);
        mv.addObject("homePage", "adminHomePage");
        return mv;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/mySchedulings/{id}/cancellSched/{schedID}")
    public String cancellSched(@PathVariable @Valid Integer id, @PathVariable @Valid Integer schedID){
        log.info("Cancelling scheduling....");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String cancellUrl;
	    //ABOVE GOES THE AUTHENTICATION PROCEDURE
        if(user.getId().equals(id) || auth.getAuthorities().stream().anyMatch(a->a
            .getAuthority().equals("ROLE_ADMIN"))){

                cancellUrl="redirect:/bswap/mySchedulings/"+id+"?schedCancelled";
                
                if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
                    if(!user.getId().equals(id)){
                        user = userServ.findById(id).orElseThrow(()->new UserNotFoundException(id));
                        //IF IT ENTERS HERE IT MEANS THE ADMIN IS TRYING TO CANCELL
                        //AND ORDER FROM WITHIN THE ADMIN PANEL, SO LET'S SEND HIM BACK THERE AGAIN
                        cancellUrl="redirect:/bswap/userSchedulings";
                    }
            
                }
                // ALL THE LOGIC YOU NEED GOES HERE, IT WONT ENTER IF THE USER DOES NOT HAVE ANY
                // PERMISSION AND THE ERROR 403 WILL BE THROWN
                //So this is an operation with the correct user or the ADMIN (both can access in this case)
                //so Let's send them back to their particular pages!
                schedulingModel sched = schedServ.findBySchedID(schedID).orElseThrow(
                    ()-> new SchedNotFoundException(schedID)
                );
                sched.setStatus("CANCELLED");
                schedServ.save(sched);
                log.info("Scheduling cancelled!");
                return cancellUrl;
            }else{   
                log.warn("Could not cancell scheduling!");
                return "redirect:/error";
            }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dayAhead")
    public ModelAndView showDA(){
        log.info("At Day Ahead Page");
        //ModelAndView mv = new ModelAndView("userRelated/dayAhead");
        //get all orders from all users for the day ahead period
        //between 7 to 22h [15 hours]
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }
        ModelAndView mv = new ModelAndView("userRelated/dayAhead");
        
        mv.addObject("homePage", homePage);

        dateHandler dh = new dateHandler();
        List<String> datesIni = dh.allDatesWithinRange();
        String startDate = datesIni.get(0);
        String endDate = datesIni.get(datesIni.size()-1);

        List<schedulingModel>scheds=schedServ.findAllBetweenRangeStr(startDate,endDate);
        List<Integer>battSum=new ArrayList<>();
        List<String>numbers=new ArrayList<>();
        numbers.add("sete");
        numbers.add("oito");
        numbers.add("nove");
        numbers.add("dez");
        numbers.add("onze");
        numbers.add("doze");
        numbers.add("treze");
        numbers.add("quatorze");
        numbers.add("quinze");
        numbers.add("dezesseis");
        numbers.add("dezesete");
        numbers.add("dezoito");
        numbers.add("dezenove");
        numbers.add("vinte");
        numbers.add("vinteum");
        numbers.add("vintedois");
        List<dayAhead>ds=new ArrayList<>();
        List<List<dayAhead>>dss=new ArrayList<>();
        dayAhead dx;
        Integer bs;
        for (var j=0;j<15;j++){
            ds=new ArrayList<>();
            bs=0;
            for (var i=j*6;i<j*6+6;i++){
                    
                //for(String sk : datesIni){
                    String sk = datesIni.get(i);
                    
                    //System.out.println(sk);
                    //schedS.add(new schedSimp(sk,"On"));
                    dx = new dayAhead();
                    dx.setDate(sk);
                    dx.setStatus("On");
                    for(schedulingModel s:scheds){
                        
                        if(!s.Status.equals("CANCELLED")){
                            //System.out.println(s.getDateFormated()+"-"+sk);
                        
                            
                            if(s.getDateFormated().equals(sk)){
                                //System.out.println("enter hre");
                               
                                dx.setStatus("Off");
                                dx.setUser(s.getUser().getNickname());
                                dx.setBattSpec(s.getBattSpec());
                                bs++;    
                            }

                            } 
                        }
                    //if(dx.getStatus().equals("On")){
                        ds.add(dx);
                        
                    //}
                    
                    //}
            }
            //dss.add(ds);
            mv.addObject(numbers.get(j), ds);
            battSum.add(bs);
            
        }
        // for(schedulingModel s:scheds){
        //     dayAhead d = new dayAhead();
        //     d.setBattSpec(s.getBattSpec());
        //     d.setDate(s.getDate().toString());
        //     d.setStatus("Off");
        //     d.setUser(s.getUser().getNickname());
        //     ds.add(d);
        // }
        mv.addObject("battSum",battSum);
        System.out.println(battSum);
        return mv;
    }

    //BATTERY RELATED
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/batteries")
    public ModelAndView showBatteries(){
        log.info("At Batteries Page");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }

        ModelAndView mv = new ModelAndView("userRelated/batteries");
        mv.addObject("homePage", homePage);
        //get all supported batteries inside the database and and them to frontend!
        List<battDTO>batts = battServ.findAllDTO();
        mv.addObject("batts", batts);
        return mv;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/batteries/{battID}/delete")
    public String deleteBattery(@PathVariable Integer battID){
        log.info("Deleting Battery...");
        Integer bd=battServ.deleteByBattID(battID);
        System.out.println(bd);
        log.info("Battery successfully deleted!");
        return "redirect:/bswap/batteries?battDeleted";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/batteries/showBattForm")
    public ModelAndView showBattForm(){
        log.info("At Battery Form");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        //retrieve info from logged user
        userModel user = userServ.findByUsername(username).orElseThrow(
            ()->new UsernameNotFoundException(username)
        );
        String homePage;
        if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
            homePage="adminHomePage";
        }else{
            homePage="userHomePage";
        }

        ModelAndView mv = new ModelAndView("userRelated/battForm");
        mv.addObject("homePage", homePage);

    
        return mv;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/batteries/addBattery")
    public String addBattery(@RequestBody @Valid @ModelAttribute("batteryModel") batteryModel batt){
        log.info("Adding a new type of battery...");
        try{
            battServ.save(batt);
            log.info("Battery added with success!");
            return "redirect:/bswap/batteries";
        }catch (Exception e){
            log.warn("Could not add new battery!");
            return "redirect:/bswap/batteries/showBattForm?battNotAdded";
        }
            
    }

        //plan RELATED
    


        @GetMapping("/plans")
        public ModelAndView showPlans(){
            log.info("Showing supported plans");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            //retrieve info from logged user
            userModel user = userServ.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username)
            );
            String homePage;
            if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
                homePage="adminHomePage";
            }else{
                homePage="userHomePage";
            }
    
            ModelAndView mv = new ModelAndView("userRelated/plan");
            mv.addObject("homePage", homePage);
            //get all supported batteries inside the database and and them to frontend!
            List<planDTO>plans = planServ.findAllDTO();
            mv.addObject("plans", plans);
            return mv;
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @GetMapping("/plans/{name}/delete")
        public String deletePlan(@PathVariable String name){
            log.info("Deleting Plan...");
            Integer pd=planServ.deleteByName(name);
            System.out.println(pd);
            log.info("Plan deleted!");
            return "redirect:/bswap/plans?planDeleted";
        }
    
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @GetMapping("/plans/showPlanForm")
        public ModelAndView showPlanForm(){
            log.info("Showing Plan Form");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            //retrieve info from logged user
            userModel user = userServ.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username)
            );
            String homePage;
            if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
                homePage="adminHomePage";
            }else{
                homePage="userHomePage";
            }
    
            ModelAndView mv = new ModelAndView("userRelated/planForm");
            mv.addObject("homePage", homePage);
    
        
            return mv;
        }
    

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @PostMapping("/plans/addPlan")
        public String addPlan(@RequestBody @Valid @ModelAttribute("planModel") planModel plan){
            log.info("Adding a new type of plan..");
            try{
                planServ.save(plan);
                log.info("New plan added to bswap!");
                return "redirect:/bswap/plans/showPlanForm?planAdded";
            }catch (Exception e){
                log.warn("Could not add new plan to bswap!");
                return "redirect:/bswap/plans/showPlanForm?planNotAdded";
            }
                
        }
        //users RELATED
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @GetMapping("/registeredUsers")
        public ModelAndView showUsers(){
            log.info("Showing registered users!");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            //retrieve info from logged user
            userModel user = userServ.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException(username)
            );
            String homePage;
            if(user.getRoles().get(0).getRoleName().toString().equals("ROLE_ADMIN")){
                homePage="adminHomePage";
            }else{
                homePage="userHomePage";
            }
    
            ModelAndView mv = new ModelAndView("userRelated/userProfiles");
            mv.addObject("homePage", homePage);
            //get all supported batteries inside the database and and them to frontend!
            List<userDTO>users = userServ.findAllDTO();
            System.out.println(users);
            mv.addObject("users", users);

            return mv;
        }


}
