package com.mysubscriptionsproject.persistence.entity;

import com.mysubscriptionsproject.core.model.Category;
import com.mysubscriptionsproject.core.model.Formule;
import com.mysubscriptionsproject.core.model.Periodicity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private double price;

    private Periodicity periodicity;

    private Category category;

    private Formule formule;

    @ManyToMany
    private List<ProfileEntity> profileEntities;

}
