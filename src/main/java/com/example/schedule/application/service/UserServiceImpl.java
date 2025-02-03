package com.example.schedule.application.service;

import com.example.schedule.presentation.Exception.ApiError;
import com.example.schedule.presentation.Exception.ApplicationException;
import com.example.schedule.presentation.Exception.ErrorMessageCode;
import com.example.schedule.presentation.dto.UserRequestDto;
import com.example.schedule.presentation.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;
import com.example.schedule.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User(dto.getUserName(), dto.getEmail());
        return userRepository.saveUser(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findUserByIdOrElseThrow(userId);
        return new UserResponseDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Long userId, String userName, String email) {
            if (!StringUtils.hasText(userName) || !StringUtils.hasText(email)) {
                throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                        List.of(new ApiError("required values", "사용자 이름과 이메일은 필수값입니다.")));
        }

        int updatedRow = userRepository.updateUser(userId, userName, email);

        if (updatedRow == 0) {
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+userId)));
        }

        User writer= userRepository.findUserByIdOrElseThrow(userId);

        return new UserResponseDto(writer);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        int deletedRow = userRepository.deleteUser(userId);

        if (deletedRow == 0) {
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+userId)));
        }
    }
}
