package com.mysubscriptionsproject.mapper;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "subscriptions", source = "subscriptions")
    UserDto toUserDto(UserEntity user);

    @Mapping(target = "subscriptions", source = "subscriptions")
    List<UserDto> toUsersDto(List<UserEntity> users);

    @Mapping(target = "user", ignore = true)
    SubscriptionDto toSubscriptionDto(SubscriptionEntity subscription);

    @Mapping(target = "subscriptions", source = "subscriptions")
    UserEntity toUserEntity(UserDto user);

    @AfterMapping
    default void setUserInSubscriptions(@MappingTarget UserEntity userEntity) {
        if (userEntity.getSubscriptions() != null) {
            userEntity.getSubscriptions().forEach(subscription -> subscription.setUser(userEntity));
        }
    }

    void updateUserEntityFromDto(UserDto dto, @MappingTarget UserEntity entity);

}
