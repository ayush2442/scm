package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entites.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    
    //user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User Dashboard: ");
        return "user/dashboard";
    }

    //user profile
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
        
        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        logger.info("User loggin in: {}" + username);

        //fetching user data from database:
        User user = userService.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
        return "user/profile";
    }
    
    //user add contact

    //user view contact
    
    //user edit  

    //user delete
}
