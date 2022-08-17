package com.smartcontactmanager.smartcontactmanager.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserConfig{
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImp();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
     
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
 
    return authProvider;  
    }
@Bean
AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 
    http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").
    antMatchers("/user/**").hasRole("USER").antMatchers("/**").
    permitAll().and().formLogin().loginPage("/login").and().csrf().disable();

    http.formLogin().defaultSuccessUrl("/user/show_contacts/0");
    
    return http.build();
}
    
}
