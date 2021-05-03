package com.conference.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "topic_offer")
public class TopicOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
