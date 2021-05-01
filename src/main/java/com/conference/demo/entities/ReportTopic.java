package com.conference.demo.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "report_topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    private Conference conference;
}
