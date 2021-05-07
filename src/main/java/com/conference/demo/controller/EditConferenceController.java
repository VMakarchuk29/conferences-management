package com.conference.demo.controller;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.exception.ConferenceNotFoundException;
import com.conference.demo.service.ConferenceService;
import com.conference.demo.service.EditConferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("update-conference")
@PreAuthorize("hasAuthority('MODERATOR')")
public class EditConferenceController {
    private static final Logger log = Logger.getLogger(EditConferenceController.class);

    private final EditConferenceService editConferenceService;
    private final ConferenceService conferenceService;

    @Autowired
    public EditConferenceController(EditConferenceService editConferenceService, ConferenceService conferenceService) {
        this.editConferenceService = editConferenceService;
        this.conferenceService = conferenceService;
    }

    @ModelAttribute("dto")
    public CreateConferenceDTO getDTO(@PathVariable("id") long id) throws ConferenceNotFoundException {
        Conference conference = conferenceService.findById(id);

        return editConferenceService.convertConferenceToDTO(conference);
    }

    @GetMapping("/{id}")
    public String showUpdatePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "update-conference";
    }

    @PostMapping("/{id}")
    public String editConference(@PathVariable("id") long id, @ModelAttribute("dto") @Valid CreateConferenceDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Update conference form has errors: " + dto);
            return "update-conference";
        }
        Conference conference = null;
        try {
            conference = editConferenceService.updateById(id, dto);
        } catch (ConferenceNotFoundException e) {
            log.error(e.getMessage());
        }
        if (conference != null)
            log.info("Success update conference: " + conference);
        return "redirect:/conferences";
    }
}
