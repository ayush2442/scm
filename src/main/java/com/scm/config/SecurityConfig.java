package com.scm.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailService;


@Configuration
public class SecurityConfig {

    // user create and login using java code with memory service
    // UserDetails user1 = User.withUsername("admin123")
    // .password("admin123")
    // .roles("ADMIN","USER")
    // .build();

    // UserDetails user2 = User.withUsername("user123")
    // .password("user123")
    // .roles("USER")
    // .build();
    
    // @Bean
    // public UserDetailsService userDetailsService() {

    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    //config of authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return daoAuthenticationProvider;
    }

    //security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        //configuration
        //url configured for which to be public and which one to be private
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home", "register", "services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        //if we have to change anything related to form login then change here
        httpSecurity.formLogin(formLogin->{

            // custom login form 
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .successForwardUrl("/user/profile")
            // .failureForwardUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password");
            // .failureHandler(new AuthenticationFailureHandler() {

            //     @Override
            //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            //             AuthenticationException exception) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            //     }
                
            // })
            // .successHandler(new AuthenticationSuccessHandler() {

            //     @Override
            //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            //             Authentication authentication) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
            //     }
                
            // })
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //oauth configurations
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login")
            .successHandler(handler);
        });

        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout")
            .logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
