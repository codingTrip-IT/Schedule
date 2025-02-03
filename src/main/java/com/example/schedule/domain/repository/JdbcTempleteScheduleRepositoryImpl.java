package com.example.schedule.domain.repository;

import com.example.schedule.presentation.dto.ScheduleResponseDto;
import com.example.schedule.domain.entity.Schedule;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTempleteScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    /* 생성자 주입 */
    public JdbcTempleteScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* 일정 저장(생성) */
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        // 현재 날짜와 시간 설정
        LocalDateTime now = LocalDateTime.now();
        schedule.setCreatedAt(now); // 작성일 설정
        schedule.setUpdatedAt(now); // 수정일 설정

        // 저장할 파라미터 매핑
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("user_id", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());
        parameters.put("deleted", false); // 기본값: 삭제되지 않음

        // 저장 후 생성된 key 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTodo(),schedule.getUserId(),schedule.getUserName(),schedule.getCreatedAt(),schedule.getUpdatedAt());
    }

    /* 일정 전체 조회 (수정일 또는 사용자 ID 필터링 가능) */
    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long userId) {

        String sql;

        // 수정일 파라미터만 입력된 경우
        if (updatedAt != null && userId == null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE DATE(s.updated_at)= ? AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";

            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt));
        }

        // 사용자 id 파라미터만 입력된 경우
        if (updatedAt == null && userId != null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE u.id = ? AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),userId);
        }

        // 수정일, 사용자 id 파라미터 둘다 입력된 경우
        if (updatedAt != null && userId != null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE DATE(s.updated_at)= ? AND u.id = ?  AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt),userId);
        }

        // 파라미터가 둘다 입력되지 않은 경우
        sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                "FROM schedule s \n" +
                " JOIN user u \n" +
                "   ON s.user_id = u.id\n" +
                "WHERE s.deleted = false \n"+
                "ORDER BY s.updated_at DESC";

        return jdbcTemplate.query(sql,scheduleRowMapper());
    }

    /* 일정 전체 조회 - 페이징 */
    @Override
    public List<ScheduleResponseDto> findAllSchedulePaging(int pageNo, int pageSize) {
        String sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                "FROM schedule s\n" +
                "JOIN user u\n" +
                "ON s.user_id = u.id AND s.deleted = false \n" +
                "ORDER BY s.updated_at DESC\n" +
                "LIMIT ? OFFSET ?";

        int offsetValue = (pageNo-1) * pageSize; //offset 값 구하는 공식

        return jdbcTemplate.query(sql,scheduleRowMapper(),pageSize,offsetValue);
    }

    /* 일정 선택 조회 */
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long scheduleId) {
        String sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                "FROM schedule s\n" +
                "         JOIN user u\n" +
                "              ON s.user_id = u.id\n" +
                "WHERE s.id = ? AND s.deleted = false\n" +
                "ORDER BY s.updated_at DESC";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), scheduleId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId));
    }

    /* 삭제여부 조회 */
    @Override
    public boolean validateDeleted(Long scheduleId) {
        return jdbcTemplate.queryForObject("select deleted from schedule where id =?", boolean.class, scheduleId);
    }

    /* ScheduleResponseDto 매핑 */
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("created_at"), formatter),
                        LocalDateTime.parse(rs.getString("updated_at"), formatter)
                );
            }
        };
    }

    /* Schedule 매핑 */
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {

                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("created_at"),formatter),
                        LocalDateTime.parse(rs.getString("updated_at"),formatter)
                );
            }
        };
    }

    /* 일정 선택 수정 */
    @Override
    public int updateSchedule(Long scheduleId, String todo, String password) {
        return jdbcTemplate.update("UPDATE schedule SET todo = ?,updated_at = NOW()" +
                    "WHERE id = ? AND password = ?",todo, scheduleId, password);
    }

    /* 비밀번호 조회 */
    @Override
    public String validatePassword(Long scheduleId) {
        return jdbcTemplate.queryForObject("select password from schedule where id =?", String.class, scheduleId);
    }

    /* 일정 선택 삭제 - soft delete 사용*/
    @Override
    public int deleteSchedule(Long scheduleId, String password) {
        return jdbcTemplate.update("UPDATE schedule SET deleted = true WHERE id = ? AND password = ?",scheduleId, password);
    }
}
