package com.conference.demo.controller;


import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private AuthController controller;


    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void badCredentialLogin() throws Exception {
        mockMvc.perform(post("/login").param("user", "VadimTest"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Disabled
    void registration() throws Exception {
        UserRegistrationDTO user = new UserRegistrationDTO();
        mockMvc.perform(post("/signup")
                .param("lastName", "")
                .param("userName", "")
                .param("email", ""))
                .andExpect(
                        MockMvcResultMatchers.model().attributeHasFieldErrors(
                                "registrationDTO", "lastName", "userName", "email"
                        )
                ).andExpect(view().name("registration"));
    }
}
