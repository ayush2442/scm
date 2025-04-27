package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entites.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler.");
        model.addAttribute("name", "Ayush");
        model.addAttribute("github", "ayush2442");
        return "home";
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("It is about page loading!!!");
        return "about";
    }
    // services route
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("It is services page loading!!!");
        return "services";
    }

    // contact page
    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    // login
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // register
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        // userForm.setName("Ayush");
        // userForm.setAbout("This is the about section...");
        model.addAttribute("userForm", userForm);
        
        return new String("register");
    }

    //processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {
        System.out.println("Processing registration...");
        //fetch the form data
        //userform
        System.out.println(userForm);
        
        //validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }
        
        //save to database

        //user service
        
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://robohash.org/mail@ashallendesign.co.uk")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://robohash.org/mail@ashallendesign.co.uk");


        User savedUser = userService.saveUser(user);

        System.out.println("User saved successfully");
        
        //message successful
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        
        //redirect to login page
        return "redirect:/register";
    }
}
