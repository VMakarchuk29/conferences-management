package com.conference.demo.service;

import com.conference.demo.dto.ProfileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileDTO buildProfileDTO(String email);

    void updateAccount(String email, ProfileDTO profileDto, MultipartFile file);
}
