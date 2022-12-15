package com.br.atos2022.bswap.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class bswapUrlAuthSuccessHandler implements AuthenticationSuccessHandler{
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication authentication) throws IOException, ServletException {
            
            try{
            handle(request,response,authentication);
            }catch (Exception e){
                System.out.println("Error: "+e);
            }
            clearAuthenticationAtributes(request);
        
    }
    
    protected void handle(
        HttpServletRequest request,
        HttpServletResponse response, 
        Authentication authentication
    ) throws Exception{
        final String targetUrl=determineTargetUrl(authentication);
        if (response.isCommitted()){
            System.out.println("Response has already been commited. Unable to redirect to: "+targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request,response,targetUrl); 
    }
  
    protected String determineTargetUrl(final Authentication authentication){
        Map<String,String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_ADMIN","/bswap/adminHomePage");//otherwise change it to /adminhomepage.html
        roleTargetUrlMap.put("ROLE_USER","/bswap/userHomePage");//otherwise change it to /userhomepage.html
        
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities){
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)){
                return roleTargetUrlMap.get(authorityName);
                // Note that this method will return the mapped URL for the first role the user has. So if a user has multiple roles,
                // the mapped URL will be the one that matches the first role given in the authorities collection.
            }
        }
        throw new IllegalStateException();
            /**
     * Removes temporary authentication-related data which may have been stored in the session
     * during the authentication process.
     */
    }

    protected void clearAuthenticationAtributes(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session==null){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
    // The determineTargetUrl – which is the core of the strategy – simply looks at the type of user (determined by the authority)
    // and picks the target URL based on this role.
    // So, an admin user – determined by the ROLE_ADMIN authority – will be redirected to the console page after login,
    // while the standard user – as determined by ROLE_USER – will be redirected to the homepage.

}
