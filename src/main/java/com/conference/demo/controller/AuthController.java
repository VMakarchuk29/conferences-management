package com.conference.demo.controller;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.exception.UserAlreadyExistException;
import com.conference.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    private static final Logger log = Logger.getLogger(AuthController.class);
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO registrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String registrationForm() {
        return "registration";
    }

    @PostMapping("/signup")
    public String registrationUser(@ModelAttribute("registrationDTO")
                                   @Valid UserRegistrationDTO userRegistrationDTO,
                                   BindingResult result) {
        if (result.hasErrors()) {
            log.warn("The user registration form has errors: " + userRegistrationDTO);
            return "registration";
        }

        try {
            userService.registerNewUserAccount(userRegistrationDTO);
            log.info("Account with email '" + userRegistrationDTO.getEmail() + "' successfully registered");
        } catch (UserAlreadyExistException e) {
            result.rejectValue("email", null, e.getMessage());//TODO replace message

            log.warn("Account already registered with that email address: " + userRegistrationDTO.getEmail());
            return "registration";
        }
        return "redirect:/signup?success";
    }
}
