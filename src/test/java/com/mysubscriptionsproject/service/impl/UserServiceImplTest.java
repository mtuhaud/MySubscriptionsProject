package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import com.mysubscriptionsproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
