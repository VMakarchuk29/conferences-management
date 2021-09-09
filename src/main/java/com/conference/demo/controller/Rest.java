package com.conference.demo.controller;

import com.conference.demo.dto.SpeakerDTO;
import com.conference.demo.entities.User;
import com.conference.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Rest {
    UserService userService;

    public Rest(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("speaker")
    public List<SpeakerDTO> getSpeakers() {
        return userService.findAllSpeakerDTO();
    }
}
