package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entites.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    //verify email

    @Autowired
    private UserRepo userRepo;
    
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {
            //user has been fetched : process accordingly

            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);

                session.setAttribute("message", Message.builder()
                .type(MessageType.green)
                .content("Your email is verified. You can now proceed with login")
                .build());
                
                return "success_page";
            }

            session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified! Token is not assosiated with user.")
                .build());

            return "error_page";
        }

        session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified! Token is not assosiated with user.")
                .build());
        
        return "error_page";
    }
    
}
