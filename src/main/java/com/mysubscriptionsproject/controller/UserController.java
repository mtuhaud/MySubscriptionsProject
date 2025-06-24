package com.mysubscriptionsproject.controller;

import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {

        return this.userService.getUser(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public void addUser(@RequestBody UserDto user) {
        this.userService.addUser(user);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public List<UserDto> getUsers() { return this.userService.getAllUsers(); }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public void updateUser(@RequestBody UserDto user, @PathVariable("id") Long id) {
        this.userService.updateUser(user, id);
    }
}
