package com.example.schedule.domain.repository;

import com.example.schedule.presentation.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;

import java.util.List;

public interface UserRepository {

    UserResponseDto saveUser(User user);

    List<UserResponseDto> findAllUsers();

    User findUserByIdOrElseThrow(Long userId);

    int updateUser(Long userId, String userName, String email);

    int deleteUser(Long userId);
}
