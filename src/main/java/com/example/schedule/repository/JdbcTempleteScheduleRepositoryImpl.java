package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.mysql.cj.result.Row;
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
import java.util.Optional;

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
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("updatedAt", schedule.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTodo(),schedule.getWriter(),schedule.getCreatedAt(),schedule.getUpdatedAt());
    }

//    @Override
//    public List<ScheduleResponseDto> findAllSchedules(Schedule schedule) {

//        String sql = "select * from schedule ";
//
//        if (schedule.getUpdatedAt() != null){
//            sql += "where DATE(updatedAt)=? ";
//            sql += "order by updatedAt desc";
//            return jdbcTemplate.query(sql,scheduleRowMapper(),schedule.getUpdatedAt());
//        }
//
//        if (schedule.getWriter() != null){
//            sql += "writer=?";
//            sql += "order by updatedAt desc";
//            return jdbcTemplate.query(sql,scheduleRowMapper(),schedule.getWriter());
//        }

//        sql += "order by updatedAt desc";



//        return jdbcTemplate.query(sql,scheduleRowMapper());
//    }

    @Override
    public Schedule findMemoByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getString("writer"),
                        LocalDateTime.parse(rs.getString("createdAt"),formatter),
                        LocalDateTime.parse(rs.getString("updatedAt"),formatter)
                );
            }
        };
    }

    @Override
    public int updateSchedule(Long id, String todo, String writer, String password) {
        return jdbcTemplate.update("UPDATE schedule SET todo = ?, writer = ?, updatedAt = NOW()" +
                "WHERE id = ? AND password = ?;",todo, writer, id, password);
    }
}
