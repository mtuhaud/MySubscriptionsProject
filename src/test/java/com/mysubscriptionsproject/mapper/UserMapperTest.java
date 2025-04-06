package com.mysubscriptionsproject.mapper;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.dto.model.Category;
import com.mysubscriptionsproject.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testSetUserInSubscriptions() {
        UserDto userDto = new UserDto();
        userDto.setName("José");
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setCategory(Category.MUSIC);
        subscriptionDto.setName("Spotify");
        SubscriptionDto subscriptionDto2 = new SubscriptionDto();
        subscriptionDto.setCategory(Category.SERIES_AND_MOVIES);
        subscriptionDto.setName("Netflix");
        ArrayList<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        subscriptionDtos.add(subscriptionDto);
        subscriptionDtos.add(subscriptionDto2);
        userDto.setSubscriptions(subscriptionDtos);

        UserEntity result = userMapper.toUserEntity(userDto);

        assertThat(userDto.getName()).isEqualTo(result.getName());
//        result.getSubscriptions().forEach(sub -> {
//            assertThat(sub.getUser()).isSameAs(result); // test du @AfterMapping
//        });
        assertThat(result.getSubscriptions().get(0).getUser().getName()).isEqualTo(userDto.getName());
    }


}
