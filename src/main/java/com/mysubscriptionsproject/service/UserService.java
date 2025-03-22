package com.mysubscriptionsproject.service;

import com.mysubscriptionsproject.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUser(Long id);
    List<UserDto> getAllUsers();
    void addUser(UserDto userDto);
    void deleteUser(Long id);
    void updateUser(UserDto user, Long id);
}
