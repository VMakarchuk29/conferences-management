package com.conference.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Entity
@Data
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
    private String gender; //TODO gender enum

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private User user;
}
