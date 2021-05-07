package com.conference.demo.controller;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.service.CreateConferenceService;
import com.conference.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/add-conference")
@PreAuthorize("hasAuthority('MODERATOR')")
public class CreateConferenceController {
    private static final Logger log = Logger.getLogger(CreateConferenceController.class);
    private final CreateConferenceService createConferenceService;
    private final UserService userService;

    @Autowired
    public CreateConferenceController(CreateConferenceService createConferenceService, UserService userService) {
        this.createConferenceService = createConferenceService;
        this.userService = userService;
    }

    @ModelAttribute("dto")
    public CreateConferenceDTO getDTO() {
        return new CreateConferenceDTO();
    }

    @GetMapping
    public String showAddForm(Model model) {
        model.addAttribute("speakers", userService.findAllSpeaker());

        return "add-conference";
    }

    @PostMapping
    public String addConference(
            @ModelAttribute("dto") @Valid CreateConferenceDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            log.error("Create conference form has errors: " + dto);
            model.addAttribute("speakers", userService.findAllSpeaker());

            return "add-conference";
        }
        System.out.println(createConferenceService.createConference(dto));
        return "redirect:/conferences";
    }
}
