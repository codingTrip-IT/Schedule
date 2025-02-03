package com.example.schedule.presentation.controller;

import com.example.schedule.presentation.dto.UserRequestDto;
import com.example.schedule.presentation.dto.UserResponseDto;
import com.example.schedule.application.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /* 생성자 주입 */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* 사용자 생성 */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    /* 사용자 전체 조회 */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    /* 사용자 선택 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /* 사용자 선택 수정 */
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("userId") Long userId, @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(userService.updateUser(userId,dto.getUserName(),dto.getEmail()),HttpStatus.OK);
    }

    /* 사용자 선택 삭제 */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
