package com.mysubscriptionsproject.controller;

import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return this.userService.getUser(id);
    }

    @PostMapping()
    public void addUser(@RequestBody UserDto user) {
        this.userService.addUser(user);
    }

    @GetMapping()
    public List<UserDto> getUsers() { return this.userService.getAllUsers(); }
}
