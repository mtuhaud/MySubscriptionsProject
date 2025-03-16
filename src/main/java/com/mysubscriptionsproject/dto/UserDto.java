package com.mysubscriptionsproject.dto;

import lombok.Data;

import java.util.List;


@Data
public class UserDto {

    private String name;

    private List<SubscriptionDto> subscriptions;

}

