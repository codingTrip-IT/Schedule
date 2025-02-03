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

    /* 생성자 주입 */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* 사용자 생성 */
    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User(dto.getUserName(), dto.getEmail());
        return userRepository.saveUser(user);
    }

    /* 사용자 전체 조회 */
    @Override
    public List<UserResponseDto> getUsers() {
        return userRepository.findAllUsers();
    }

    /* 사용자 선택 조회 */
    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findUserByIdOrElseThrow(userId);
        return new UserResponseDto(user);
    }

    /* 사용자 선택 수정 */
    @Transactional
    @Override
    public UserResponseDto updateUser(Long userId, String userName, String email) {
        // 필수 값 검증 (userName(사용자명)이 없거나, email(이메일)이 없을 경우 예외처리)
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(email)) {
            throw new ApplicationException(ErrorMessageCode.BAD_REQUEST,
                    List.of(new ApiError("required values", "사용자 이름과 이메일은 필수값입니다.")));
        }

        //  일정 수정
        int updatedRow = userRepository.updateUser(userId, userName, email);
        if (updatedRow == 0) { // 존재하지 않는 일정이면 예외 발생
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+userId)));
        }

        // 수정된 일정 반환
        User user= userRepository.findUserByIdOrElseThrow(userId);
        return new UserResponseDto(user);
    }

    /* 사용자 선택 삭제 */
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        // 일정 삭제 (Soft Delete)
        int deletedRow = userRepository.deleteUser(userId);
        if (deletedRow == 0) { // 존재하지 않는 일정이면 예외 발생
            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
                    List.of(new ApiError("id", "입력한 id가 존재하지 않습니다."+userId)));
        }
    }
}
