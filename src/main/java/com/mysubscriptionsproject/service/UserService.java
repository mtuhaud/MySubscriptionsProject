package com.mysubscriptionsproject.service;

import com.mysubscriptionsproject.dto.UserDto;

public interface UserService {

    UserDto getUser(Long id);

    void addUser(UserDto userDto);

}
