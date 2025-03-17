package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.entity.SubscriptionEntity;
import com.mysubscriptionsproject.entity.UserEntity;
import com.mysubscriptionsproject.repository.UserRepository;
import com.mysubscriptionsproject.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getUser(Long id) {
        var entity = this.userRepository.findById(id).orElse(null);
        UserDto user = new UserDto();
        // TODO : gérer prochainement nullpointer avec une classe @ControllerAdvice
        user.setName(entity.getName());

        var subs = entity.getSubscriptions().stream().map(
                sub -> {
                    var subDto = new SubscriptionDto();
                    subDto.setName(sub.getName());
                    subDto.setPrice(sub.getPrice());
                    subDto.setFormule(sub.getFormule());
                    subDto.setCategory(sub.getCategory());
                    return subDto;
                }
        ).toList();

        user.setSubscriptions(subs);

        return user;
    }

    @Override
    // TODO : créer un DTO uniquement pour cet appel sans user dans la souscription
    public List<UserDto> getAllUsers() {
        var usersEntity = this.userRepository.findAll();
        return usersEntity.stream().map(
                user -> {
                        var userDto = new UserDto();
                        userDto.setName(user.getName());
                        List<SubscriptionDto> subscriptionDto = new ArrayList<>();
                        for(SubscriptionEntity subsEntity : user.getSubscriptions()) {
                            var subDto = new SubscriptionDto();
                            subDto.setName(subsEntity.getName());
                            subDto.setPrice(subsEntity.getPrice());
                            subDto.setFormule(subsEntity.getFormule());
                            subDto.setCategory(subsEntity.getCategory());
                            subscriptionDto.add(subDto);
                        }
                        userDto.setSubscriptions(subscriptionDto);
                        return userDto;
                }
        ).toList();
    }

    @Override
    @Transactional
    public void addUser(UserDto user) {
        var entity = new UserEntity();
        entity.setName(user.getName());
        var subsEntity = user.getSubscriptions().stream().map(
                sub -> {
                    var subEntity = new SubscriptionEntity();
                    subEntity.setName(sub.getName());
                    subEntity.setPrice(sub.getPrice());
                    subEntity.setFormule(sub.getFormule());
                    subEntity.setCategory(sub.getCategory());
                    subEntity.setUser(entity);
                    return subEntity;
                }
        ).toList();

        entity.setSubscriptions(subsEntity);

        this.userRepository.save(entity);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.findById(id).ifPresent(this.userRepository::delete);
    }
}
