package com.conference.demo.entities;

import com.conference.demo.entities.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Entity
//@Data
@Getter//todo
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_info")
public class UserInfo {
    @Id
    @Column(name = "account_id")
    private long id;

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;
    private String filename;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private User user;
}
