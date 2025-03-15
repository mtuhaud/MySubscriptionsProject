package com.mysubscriptionsproject.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy ="profiles")
    private List<SubscriptionEntity> subscription;

}
