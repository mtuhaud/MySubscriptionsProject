package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.common.exception.EntityNotFoundException;
import com.mysubscriptionsproject.common.exception.SubscriptionException;
import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import com.mysubscriptionsproject.mapper.UserMapper;
import com.mysubscriptionsproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void testGetUser() {
        var id = 1L;

        var entity = new UserEntity();
        entity.setId(id);
        entity.setName("Toto");
        var subscription = new SubscriptionEntity();
        subscription.setName("Netflix");
        entity.setSubscriptions(Collections.singletonList(subscription));

        var dto = new UserDto();
        dto.setName("Toto");
        var subscriptionDto = new SubscriptionDto();
        subscriptionDto.setName("Netflix");
        dto.setSubscriptions(Collections.singletonList(subscriptionDto));


        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMapper.toUserDto(entity)).thenReturn(dto);

        var result = userService.getUser(id);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(entity.getName());
        assertThat(result.getSubscriptions()).hasSize(1);
        assertThat(result.getSubscriptions().get(0).getName()).isEqualTo("Netflix");
    }

    @Test
    void testGetUser_withEntityNotFoundException() throws EntityNotFoundException {
        var id = 1L;

        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    void testUpdateUser() {
        var id = 1L;
        var userDto = new UserDto();
        var userEntity = new UserEntity();
        userEntity.setName("oldUserName");
        var subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setName("Apple Music");
        userEntity.setSubscriptions(new ArrayList<>(Collections.singletonList(subscriptionEntity)));

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        userService.updateUser(userDto, id);

        verify(userRepository).findById(id);
        assertThat(userEntity.getSubscriptions()).isEmpty();
        verify(userMapper).updateUserEntityFromDto(userDto, userEntity);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testAddUser() {
        var userDto = new UserDto();
        userDto.setName("Polo");
        userDto.setSubscriptions(List.of(new SubscriptionDto()));

        var userEntity = new UserEntity();
        userEntity.setName("Polo");
        userEntity.setSubscriptions(List.of(new SubscriptionEntity()));

        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);

        userService.addUser(userDto);

        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityArgumentCaptor.capture());

        UserEntity userEntityCaptor = userEntityArgumentCaptor.getValue();
        assertThat(userEntityCaptor.getName()).isEqualTo("Polo");
    }

    @Test
    void testAddUser_withSubscriptionException() throws EntityNotFoundException {
        SubscriptionException exception = assertThrows(SubscriptionException.class, ()
        -> userService.addUser(new UserDto()));
        assertThat(exception.getMessage()).isEqualTo("Un nom d'utilisateur doit être renseigné");

    }

    @Test
    void testAddUser_withNoSubcriptionException() throws RuntimeException {
        var userDto = new UserDto();
        userDto.setName("Polo");
        RuntimeException exception = assertThrows(SubscriptionException.class,
                () -> userService.addUser(userDto));
        assertThat(exception.getMessage()).isEqualTo("Un abonnement doit être présent");
    }

    @Test
    void testDeleteUser() {
        var id = 1L;
        var userEntity = new UserEntity();
        userEntity.setName("Polo");

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        userService.deleteUser(id);

        verify(userRepository).delete(userEntity);
    }

    @Test
    void testGetAllUsers() {
        var entityList = new ArrayList<UserEntity>();
        var entity = new UserEntity();
        entity.setName("Polo");
        entity.setSubscriptions(List.of(new SubscriptionEntity()));
        var entity2 = new UserEntity();
        entity2.setName("Toto");
        entity2.setSubscriptions(List.of(new SubscriptionEntity()));
        entityList.add(entity);
        entityList.add(entity2);

        var dtoList = new ArrayList<UserDto>();
        var userDto = new UserDto();
        userDto.setName("Polo");
        userDto.setSubscriptions(List.of(new SubscriptionDto()));
        var userDto2 = new UserDto();
        userDto2.setName("Toto");
        userDto2.setSubscriptions(List.of(new SubscriptionDto()));
        dtoList.add(userDto);
        dtoList.add(userDto2);

        when(userRepository.findAll()).thenReturn(entityList);
        when(userMapper.toUsersDto(entityList)).thenReturn(dtoList);

        var result = userService.getAllUsers();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo(entity.getName());
        assertThat(result.get(1).getName()).isEqualTo(entity2.getName());
    }


}
