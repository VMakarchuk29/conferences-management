package com.conference.demo.controller;

import com.conference.demo.dto.ProfileDTO;
import com.conference.demo.service.ProfileService;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @ModelAttribute("profileDto")
    public ProfileDTO profileDto(Principal principal) {
        return profileService.buildProfileDTO(principal.getName());
    }

    @GetMapping
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User with email '" + principal.getName() + "' not found")));
        return "profile";
    }
    @GetMapping("/edit")
    public String showEditProfile() {
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateAccount(
            @ModelAttribute("profileDto") @Valid ProfileDTO profileDto,
            @RequestParam("file") MultipartFile file,
            Principal principal
    ) {
        profileService.updateAccount(principal.getName(), profileDto, file);
        return "redirect:/profile";
    }
}
