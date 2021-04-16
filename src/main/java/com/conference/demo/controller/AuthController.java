package com.conference.demo.controller;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.entities.User;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
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
            return "registration";
        }
        User existing = userService.findByEmail(userRegistrationDTO.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        userService.save(userRegistrationDTO);
        return "redirect:/registration?success";
    }
}
