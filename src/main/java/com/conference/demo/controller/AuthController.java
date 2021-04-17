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
import java.util.Optional;

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

        Optional<User> existing = userService.findByEmail(userRegistrationDTO.getEmail());

        if (existing.isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        userService.save(userRegistrationDTO);
        return "redirect:/signup?success";
    }
}
