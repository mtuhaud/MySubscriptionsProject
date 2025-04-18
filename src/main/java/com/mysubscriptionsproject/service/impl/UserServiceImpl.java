package com.mysubscriptionsproject.service.impl;

import com.mysubscriptionsproject.common.exception.EntityNotFoundException;
import com.mysubscriptionsproject.common.exception.SubscriptionException;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.mapper.UserMapper;
import com.mysubscriptionsproject.repository.UserRepository;
import com.mysubscriptionsproject.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUser(Long id) {
        var entity = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return userMapper.toUserDto(entity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        var usersEntity = this.userRepository.findAll();
        return userMapper.toUsersDto(usersEntity);
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

        this.userRepository.save(userMapper.toUserEntity(user));
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

        userEntity.getSubscriptions().clear();

        userMapper.updateUserEntityFromDto(user, userEntity);

        this.userRepository.save(userEntity);
    }

}
