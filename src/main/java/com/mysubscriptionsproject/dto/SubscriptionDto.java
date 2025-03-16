package com.mysubscriptionsproject.dto;

import com.mysubscriptionsproject.dto.model.Category;
import com.mysubscriptionsproject.dto.model.Formule;
import lombok.Data;

@Data
public class SubscriptionDto {

    private String name;
    private double price;
    private Category category;
    private Formule formule;
}
