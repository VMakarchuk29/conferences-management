package com.conference.demo.controller;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.service.CreateConferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/add-conference")
public class CreateConferenceController {
    private static final Logger log = Logger.getLogger(CreateConferenceController.class);
    private final CreateConferenceService createConferenceService;

    @Autowired
    public CreateConferenceController(CreateConferenceService createConferenceService) {
        this.createConferenceService = createConferenceService;
    }

    @ModelAttribute("dto")
    public CreateConferenceDTO getDTO() {
        return new CreateConferenceDTO();
    }

    @GetMapping
    public String showAddForm() {
        return "add-conference";
    }

    @PostMapping
    public String addConference(@ModelAttribute("dto") @Valid CreateConferenceDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            log.error("The add conference form has errors: " + dto);
            return "add-conference";
        }
        System.out.println(createConferenceService.createConference(dto));
        return "redirect:/conferences";
    }
}
