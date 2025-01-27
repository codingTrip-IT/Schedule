package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
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
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("scheduleId");

        LocalDateTime now = LocalDateTime.now();
        schedule.setCreatedAt(now); // 작성일 설정
        schedule.setUpdatedAt(now); // 수정일 설정

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writerId", schedule.getWriterId());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("updatedAt", schedule.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTodo(),schedule.getWriterId(),schedule.getName(),schedule.getCreatedAt(),schedule.getUpdatedAt());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, Long writerId) {

        String sql;

        if (updatedAt != null && writerId == null){
            sql = "SELECT s.scheduleId, s.todo, w.writerId, w.name, s.createdAt, s.updatedAt\n" +
                    "FROM schedule s\n" +
                    "         JOIN writer w\n" +
                    "              ON s.writerId = w.writerId\n" +
                    "WHERE DATE(s.updatedAt)= ?\n" +
                    "ORDER BY s.updatedAt DESC";

            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt));
        }

        if (updatedAt == null && writerId != null){
            sql = "SELECT s.scheduleId, s.todo, w.writerId, w.name, s.createdAt, s.updatedAt\n" +
                    "FROM schedule s\n" +
                    "         JOIN writer w\n" +
                    "              ON s.writerId = w.writerId\n" +
                    "WHERE w.writerId = ?\n" +
                    "ORDER BY s.updatedAt DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),writerId);
        }

        if (updatedAt != null && writerId != null){
            sql = "SELECT s.scheduleId, s.todo, w.writerId, w.name, s.createdAt, s.updatedAt\n" +
                    "FROM schedule s\n" +
                    "         JOIN writer w\n" +
                    "              ON s.writerId = w.writerId\n" +
                    "WHERE DATE(s.updatedAt)= ? AND w.writerId = ?\n" +
                    "ORDER BY s.updatedAt DESC";
            return jdbcTemplate.query(sql,scheduleRowMapper(),String.valueOf(updatedAt),writerId);
        }

        sql = "SELECT s.scheduleId, s.todo, w.writerId, w.name, s.createdAt, s.updatedAt\n" +
                "FROM schedule s \n" +
                " JOIN writer w \n" +
                "   ON s.writerId = w.writerId \n" +
                "ORDER BY s.updatedAt DESC";

        return jdbcTemplate.query(sql,scheduleRowMapper());
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long scheduleId) {
        String sql = "SELECT s.scheduleId, s.todo, w.writerId, w.name, s.createdAt, s.updatedAt\n" +
                "FROM schedule s\n" +
                "         JOIN writer w\n" +
                "              ON s.writerId = w.writerId\n" +
                "WHERE s.scheduleId = ?\n" +
                "ORDER BY s.updatedAt DESC";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), scheduleId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId));
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new ScheduleResponseDto(
                        rs.getLong("scheduleId"),
                        rs.getString("todo"),
                        rs.getLong("writerId"),
                        rs.getString("name"),
                        LocalDateTime.parse(rs.getString("createdAt"), formatter),
                        LocalDateTime.parse(rs.getString("updatedAt"), formatter)
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
                        rs.getLong("scheduleId"),
                        rs.getString("todo"),
                        rs.getLong("writerId"),
                        rs.getString("name"),
                        LocalDateTime.parse(rs.getString("createdAt"),formatter),
                        LocalDateTime.parse(rs.getString("updatedAt"),formatter)
                );
            }
        };
    }

    @Override
    public int updateSchedule(Long scheduleId, String todo, String password) {
        return jdbcTemplate.update("UPDATE schedule SET todo = ?,updatedAt = NOW()" +
                "WHERE scheduleId = ? AND password = ?",todo, scheduleId, password);
    }

    @Override
    public int deleteSchedule(Long scheduleId, String password) {
        return jdbcTemplate.update("delete from schedule where scheduleId=? and password=?",scheduleId,password);
    }
}
