package com.example.schedule.application.service;

import com.example.schedule.presentation.dto.UserRequestDto;
import com.example.schedule.presentation.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);

    List<UserResponseDto> getUsers();

    UserResponseDto getUser(Long userId);

    UserResponseDto updateUser(Long userId, String userName, String email);

    void deleteUser(Long userId);
}
