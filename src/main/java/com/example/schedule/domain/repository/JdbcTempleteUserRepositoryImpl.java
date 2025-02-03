package com.example.schedule.domain.repository;

import com.example.schedule.presentation.dto.UserResponseDto;
import com.example.schedule.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTempleteUserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    /* 생성자 주입 */
    public JdbcTempleteUserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* 사용자 저장(생성) */
    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        // 현재 날짜와 시간 설정
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now); // 작성일 설정
        user.setUpdatedAt(now); // 수정일 설정

        // 저장할 파라미터 매핑
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUserName());
        parameters.put("email", user.getEmail());
        parameters.put("created_at", user.getCreatedAt());
        parameters.put("updated_at", user.getUpdatedAt());
        parameters.put("deleted", false);

        // 저장 후 생성된 key 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(),user.getUserName(),user.getEmail(),user.getCreatedAt(),user.getUpdatedAt());
    }

    /* 사용자 전체 조회 */
    @Override
    public List<UserResponseDto> findAllUsers() {
        return jdbcTemplate.query("select * from user where deleted = false order by updated_at desc",userRowMapper());
    }

    /* 사용자 선택 조회 */
    @Override
    public User findUserByIdOrElseThrow(Long userId) {
        List<User> result = jdbcTemplate.query("select * from user  where id = ? AND deleted = false ", userRowMapperV2(), userId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + userId));
    }

    /* UserResponseDto 매핑 */
    private RowMapper<UserResponseDto> userRowMapper() {
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new UserResponseDto(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter)
                );
            }
        };
    }

    /* User 매핑 */
    private RowMapper<User> userRowMapperV2() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter)
                );
            }
        };
    }

    /* 사용자 선택 수정 */
    @Override
    public int updateUser(Long userId, String name, String email) {
        return jdbcTemplate.update("UPDATE user SET username = ?, email = ?, updated_at = NOW()" +
                "WHERE id = ?;",name, email, userId);
    }

    /* 사용자 선택 삭제 */
    @Override
    public int deleteUser(Long userId) {
        return jdbcTemplate.update("UPDATE user SET deleted = true WHERE id = ?",userId);
    }
}
