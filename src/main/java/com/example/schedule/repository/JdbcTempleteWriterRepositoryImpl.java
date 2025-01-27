package com.example.schedule.repository;

import com.example.schedule.dto.WriterResponseDto;
import com.example.schedule.entity.Writer;
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
public class JdbcTempleteWriterRepositoryImpl implements WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTempleteWriterRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public WriterResponseDto saveWriter(Writer writer) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("writer").usingGeneratedKeyColumns("writerId");

        LocalDateTime now = LocalDateTime.now();
        writer.setCreatedAt(now); // 작성일 설정
        writer.setUpdatedAt(now); // 수정일 설정

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", writer.getName());
        parameters.put("email", writer.getEmail());
        parameters.put("createdAt", writer.getCreatedAt());
        parameters.put("updatedAt", writer.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new WriterResponseDto(key.longValue(),writer.getName(),writer.getEmail(),writer.getCreatedAt(),writer.getUpdatedAt());
    }

    @Override
    public List<WriterResponseDto> findAllWriters() {
        return jdbcTemplate.query("select * from writer order by updatedAt desc",writerRowMapper());
    }

    @Override
    public Writer findWriterByIdOrElseThrow(Long writerId) {
        List<Writer> result = jdbcTemplate.query("select * from writer where writerId = ?", writerRowMapperV2(), writerId);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + writerId));
    }

    private RowMapper<WriterResponseDto> writerRowMapper() {
        return new RowMapper<WriterResponseDto>() {
            @Override
            public WriterResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new WriterResponseDto(
                        rs.getLong("writerId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        LocalDateTime.parse(rs.getString("createdAt"), formatter),
                        LocalDateTime.parse(rs.getString("updatedAt"), formatter)
                );
            }
        };
    }
    private RowMapper<Writer> writerRowMapperV2() {
        return new RowMapper<Writer>() {
            @Override
            public Writer mapRow(ResultSet rs, int rowNum) throws SQLException {
                // 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new Writer(
                        rs.getLong("writerId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        LocalDateTime.parse(rs.getString("createdAt"), formatter),
                        LocalDateTime.parse(rs.getString("updatedAt"), formatter)
                );
            }
        };
    }

    @Override
    public int updateWriter(Long writerId, String name, String email) {
        return jdbcTemplate.update("UPDATE writer SET name = ?, email = ?, updatedAt = NOW()" +
                "WHERE writerId = ?;",name, email, writerId);
    }

    @Override
    public int deleteWriter(Long writerId) {
        return jdbcTemplate.update("delete from writer where writerId=?",writerId);
    }
}
