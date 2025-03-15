package com.mysubscriptionsproject.core.model;

import lombok.Data;

@Data
public class Subscription {

    private String name;
    private double price;
    private Category category;
    private Formule formule;
    private Periodicity periodicity;
}
