package com.conference.demo.entities;

import com.conference.demo.entities.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String email;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;

    @Singular
    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL)
    private List<ReportTopic> speakersReports = new ArrayList<>();
}
