package com.mysubscriptionsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysubscriptionsproject.dto.SubscriptionDto;
import com.mysubscriptionsproject.dto.UserDto;
import com.mysubscriptionsproject.dto.model.Category;
import com.mysubscriptionsproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUser() throws Exception {
        var id = 1L;
        var userDto = new UserDto();
        userDto.setName("Toto");
        var subscriptionDto = new SubscriptionDto();
        subscriptionDto.setCategory(Category.MUSIC);
        var subscriptionList = List.of(subscriptionDto);
        userDto.setSubscriptions(subscriptionList);

        when(userService.getUser(1L)).thenReturn(userDto);


        mockMvc.perform(get("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.subscriptions[0].category").value("MUSIC"));

    }

    @Test
    void testDeleteUser() throws Exception {
        var id = 1L;

        mockMvc.perform(delete("/api/v1/users/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void getUsers() throws Exception {
        List<UserDto> userDtos = new ArrayList<>();
        UserDto userDto1 = new UserDto();
        userDto1.setName("John");
        UserDto userDto2 = new UserDto();
        userDto2.setName("Tom");
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        when(userService.getAllUsers()).thenReturn(userDtos);

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(userDto1.getName()))
                .andExpect(jsonPath("$[1].name").value(userDto2.getName()));


    }

    @Test
    void addUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Max");
        var subscriptionDto = new SubscriptionDto();
        subscriptionDto.setCategory(Category.MUSIC);
        userDto.setSubscriptions(List.of(subscriptionDto));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto)))
                        .andExpect(status().isOk());

        verify(userService).addUser(userDto);
    }

    @Test
    void updateUser() throws Exception {
        var id = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Max");

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        verify(userService).updateUser(userDto, id);
    }


}
