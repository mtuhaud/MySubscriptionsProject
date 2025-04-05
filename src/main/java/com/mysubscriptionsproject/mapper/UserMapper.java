package com.mysubscriptionsproject.mapper;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "subscriptions", source = "subscriptions")
    UserDto toUserDto(UserEntity user);

    @Mapping(target = "user", ignore = true)
    SubscriptionDto toSubscriptionDto(SubscriptionEntity subscription);

    // TODO : terminer mapping restant
}
