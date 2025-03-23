package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import com.mysubscriptionsproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Test
    void testGetUser() {
        var id = 1L;
        var entity = new UserEntity();
        entity.setId(id);
        entity.setName("Toto");
        var subscription = new SubscriptionEntity();
        subscription.setName("Netflix");
        entity.setSubscriptions(Collections.singletonList(subscription));

        when(userRepository.findById(id)).thenReturn(Optional.of(entity));

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
    void testGetUser_withNoSubcriptionException() throws RuntimeException {
        var id = 1L;
        var entity = new UserEntity();
        entity.setId(id);
        entity.setName("Toto");

        when(userRepository.findById(id)).thenReturn(Optional.of(entity));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.getUser(id));
                assertThat(exception.getMessage()).isEqualTo("Un abonnement doit être présent");
    }

    @Test
    void testUpdateUser() {
        var id = 1L;
        var userDto = new UserDto();
        userDto.setName("newUserName");
        var subscriptionDto = new SubscriptionDto();
        subscriptionDto.setName("Deezer");
        userDto.setSubscriptions(new ArrayList<>(Collections.singletonList(subscriptionDto)));
        var userEntity = new UserEntity();
        userEntity.setName("oldUserName");
        var subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setName("Apple Music");
        userEntity.setSubscriptions(new ArrayList<>(Collections.singletonList(subscriptionEntity)));

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

        userService.updateUser(userDto, id);

        assertThat(userDto.getName()).isEqualTo(userEntity.getName());
        assertThat(userEntity.getSubscriptions().get(0).getName()).isEqualTo("Deezer");
        verify(userRepository).save(userEntity);
    }

    @Test
    void testAddUser() {
        var userDto = new UserDto();
        userDto.setName("Polo");
        userDto.setSubscriptions(List.of(new SubscriptionDto()));

        userService.addUser(userDto);

        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityArgumentCaptor.capture());

        UserEntity userEntity = userEntityArgumentCaptor.getValue();
        assertThat(userEntity.getName()).isEqualTo("Polo");
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

        when(userRepository.findAll()).thenReturn(entityList);

        var result = userService.getAllUsers();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo(entity.getName());
        assertThat(result.get(1).getName()).isEqualTo(entity2.getName());
    }


}
