package com.br.atos2022.bswap.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import jakarta.servlet.FilterRegistration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class bswapSecurityConfig {
    
    @Bean
    SecurityFilterChain defaultSecurityFilterChain (HttpSecurity http) throws Exception{
        http
            .csrf().disable()//this way we can make modifications by using our endpoints!
            .authorizeHttpRequests()
            .requestMatchers("/resources/**","/userRelated/**","/images/**").permitAll()
            
            .requestMatchers("/bswap/myAccount/**","/bswap/myProfile/**","/bswap/mySchedulings/**","/bswap/Schedules/**","/bswap/userHomePage","/bswap/adminHomePage","/bswap/dayAhead","/bswap/batteries/**","/bswap/plans/**").authenticated()
            .requestMatchers("/bswap","/bswap/register","/bswap/login").permitAll()
            
            .anyRequest().authenticated()
            .and().formLogin()
            //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Desliga a criacao de sessoes pelo Spring Security
            .loginPage("/bswap/login")
            .successHandler(myAuthenticationSuccessHandler())
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/bswap/Forbiden")
            .and()
            .logout()
            .logoutSuccessUrl("/bswap/login")
            .permitAll()
            .and()
            //and().formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/userhomepage",true).permitAll())
            .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();    
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new bswapUrlAuthSuccessHandler();
    }

    // @Bean
    // public FilterRegistrationBean hiddenHttpMethodFilter(){
    //     FilterRegistrationBean filterRegBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
    //     filterRegBean.setUrlPatterns(Arrays.asList("/*"));
    //     return filterRegBean;
    // }


}
