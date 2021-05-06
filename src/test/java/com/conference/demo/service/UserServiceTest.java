package com.conference.demo.service;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.entities.User;
import com.conference.demo.entities.UserInfo;
import com.conference.demo.entities.enums.Gender;
import com.conference.demo.entities.enums.Role;
import com.conference.demo.exception.UserAlreadyExistException;
import com.conference.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSave() {
        User user = buildUser();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);
        assertEquals(user, result);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByEmail() {
        User user = buildUser();
        Optional<User> userOptional = Optional.ofNullable(user);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(userOptional);

        Optional<User> result = userService.findByEmail(user.getEmail());

        assertEquals(result, userOptional);

        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void registerNewUserAccountIfAccExist() {
        UserRegistrationDTO userDTO = buildUserRegistrationDTO();
        User user = buildUser();

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDTO));

        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
    }

    @Test
    void loadUserByUsername() {
    }

    private UserRegistrationDTO buildUserRegistrationDTO() {
        return UserRegistrationDTO.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .userName("Username")
                .email("email@gmail.com")
                .password("1111")
                .confirmPassword("1111")
                .birthday("2000-07-29")
                .phoneNumber("+380450154257")
                .gender("male").terms(true)
                .build();

    }


    private User buildUser() {
        return User.builder()
                .id(1L)
                .userName("Username")
                .email("email@gmail.com")
                .password("1111")
                .role(Role.USER)
                .userInfo(buildUserInfo())
                .build();
    }

    private UserInfo buildUserInfo() {
        return UserInfo.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .birthday(LocalDate.parse("2000-07-29"))
                .phoneNumber("+380450154257")
                .createdAt(LocalDateTime.now())
                .gender(Gender.MALE)
                .build();
    }
}
