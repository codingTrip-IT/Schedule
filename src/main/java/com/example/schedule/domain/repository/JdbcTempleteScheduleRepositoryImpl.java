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

    public JdbcTempleteScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        schedule.setCreatedAt(now); // 작성일 설정
        schedule.setUpdatedAt(now); // 수정일 설정

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("user_id", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());
        parameters.put("deleted", false);

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTodo(),schedule.getUserId(),schedule.getUserName(),schedule.getCreatedAt(),schedule.getUpdatedAt());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long userId) {

        String sql;

        if (updatedAt != null && userId == null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE DATE(s.updated_at)= ? AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";

            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt));
        }

        if (updatedAt == null && userId != null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE u.id = ? AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),userId);
        }

        if (updatedAt != null && userId != null){
            sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                    "FROM schedule s\n" +
                    "         JOIN user u\n" +
                    "              ON s.user_id = u.id\n" +
                    "WHERE DATE(s.updated_at)= ? AND u.id = ?  AND s.deleted = false\n" +
                    "ORDER BY s.updated_at DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt),userId);
        }

        sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                "FROM schedule s \n" +
                " JOIN user u \n" +
                "   ON s.user_id = u.id\n" +
                "WHERE s.deleted = false \n"+
                "ORDER BY s.updated_at DESC";

        return jdbcTemplate.query(sql,scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedulePaging(int pageNo, int pageSize) {
        String sql = "SELECT s.id, s.todo, s.user_id, u.username, s.created_at, s.updated_at\n" +
                "FROM schedule s\n" +
                "JOIN user u\n" +
                "ON s.user_id = u.id AND s.deleted = false \n" +
                "ORDER BY s.updated_at DESC\n" +
                "LIMIT ? OFFSET ?";

        int offsetValue = (pageNo-1) * pageSize;

        return jdbcTemplate.query(sql,scheduleRowMapper(),pageSize,offsetValue);
    }

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

    @Override
    public boolean validateDeleted(Long scheduleId) {
        return jdbcTemplate.queryForObject("select deleted from schedule where id =?", boolean.class, scheduleId);
    }

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

//    private RowMapper<Schedule> scheduleRowMapperV3() {
//        return new RowMapper<Schedule>() {
//            @Override
//            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
//                // 문자열을 LocalDateTime으로 변환
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//                return new Schedule(
//                        rs.getLong("id"),
//                        rs.getString("todo"),
//                        rs.getLong("user_id"),
//                        LocalDateTime.parse(rs.getString("created_at"),formatter),
//                        LocalDateTime.parse(rs.getString("updated_at"),formatter),
//                        rs.getBoolean("deleted")
//                );
//            }
//        };
//    }

    @Override
    public int updateSchedule(Long scheduleId, String todo, String password) {
        return jdbcTemplate.update("UPDATE schedule SET todo = ?,updated_at = NOW()" +
                    "WHERE id = ? AND password = ?",todo, scheduleId, password);
    }

    @Override
    public String validatePassword(Long scheduleId) {
        return jdbcTemplate.queryForObject("select password from schedule where id =?", String.class, scheduleId);
    }

    /* soft delete로 수정 */
    @Override
    public int deleteSchedule(Long scheduleId, String password) {
        return jdbcTemplate.update("UPDATE schedule SET deleted = true "+
                "WHERE id = ? AND password = ?",scheduleId, password);
    }
}
