package com.conference.demo.controller;

import com.conference.demo.entities.User;
import com.conference.demo.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user-list")
public class UserListController {
    private final UserListService userListService;

    @Autowired
    public UserListController(UserListService userListService) {
        this.userListService = userListService;
    }

    @GetMapping
    public String showUserList(Model model, Pageable pageable) {
        Page<User> pages = userListService.findAllUsers(pageable);

        model.addAttribute("pages", pages);
        return "user-list";
    }
}
