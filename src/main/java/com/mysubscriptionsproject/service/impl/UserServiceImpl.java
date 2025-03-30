package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.common.exception.EntityNotFoundException;
import com.mysubscriptionsproject.common.exception.SubscriptionException;
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
        var entity = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return mapUserEntityToUserDto(entity);
    }

    @Override
    // TODO : créer un DTO uniquement pour cet appel sans user dans la souscription
    public List<UserDto> getAllUsers() {
        var usersEntity = this.userRepository.findAll();
        return mapUserEntityToUsersDto(usersEntity);
    }

    @Override
    @Transactional
    public void addUser(UserDto user) {
        if(user.getName() == null) {
            throw new SubscriptionException("Un nom d'utilisateur doit être renseigné");
        }
        if(user.getSubscriptions() == null) {
            throw new SubscriptionException("Un abonnement doit être présent");
        }
        var entity = mapUserDtoToUserEntity(user);
        this.userRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        this.userRepository.findById(id).ifPresent(this.userRepository::delete);
    }

    @Override
    @Transactional
    public void updateUser(UserDto user, Long id) {
        var userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        mapUpdateUser(user, userEntity);
        this.userRepository.save(userEntity);
    }

    // TODO: créer classe mapping et mettre en place MapStruct
    private static UserDto mapUserEntityToUserDto(UserEntity entity) {
        UserDto user = new UserDto();
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
    private static List<UserDto> mapUserEntityToUsersDto(List<UserEntity> usersEntity) {
        return usersEntity.stream().map(
                user -> {
                    var userDto = new UserDto();
                    userDto.setName(user.getName());
                    List<SubscriptionDto> subscriptionDto = new ArrayList<>();
                    for (SubscriptionEntity subsEntity : user.getSubscriptions()) {
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

    private static SubscriptionEntity mapSubDtoToEntity(SubscriptionDto subDto, UserEntity userEntity) {
        var subEntity = new SubscriptionEntity();
        subEntity.setName(subDto.getName());
        subEntity.setPrice(subDto.getPrice());
        subEntity.setFormule(subDto.getFormule());
        subEntity.setCategory(subDto.getCategory());
        subEntity.setUser(userEntity);
        return subEntity;

    }

    private static UserEntity mapUserDtoToUserEntity(UserDto userDto) {
        var entity = new UserEntity();
        entity.setName(userDto.getName());
        var  subsEntity = userDto.getSubscriptions().stream().map(
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
        return entity;
    }

    private static void mapUpdateUser(UserDto user, UserEntity userEntity) {
        userEntity.setName(user.getName());
        userEntity.getSubscriptions().clear();
        for(SubscriptionDto subDto : user.getSubscriptions()){
            userEntity.getSubscriptions().add(mapSubDtoToEntity(subDto, userEntity));
        }
    }
}
