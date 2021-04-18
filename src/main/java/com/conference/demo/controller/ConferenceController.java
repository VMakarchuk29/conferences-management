package com.conference.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

    @GetMapping
    public String showConferences() {
        return "index";
    }
}
