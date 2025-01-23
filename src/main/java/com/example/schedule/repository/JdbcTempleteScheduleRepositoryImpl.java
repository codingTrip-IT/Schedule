package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
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
        schedule.setCreatedAt(now); // 생성일 설정
        schedule.setUpdatedAt(now); // 수정일 설정

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("updatedAt", schedule.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTodo(),schedule.getWriter(),schedule.getCreatedAt(),schedule.getUpdatedAt());
    }
}
