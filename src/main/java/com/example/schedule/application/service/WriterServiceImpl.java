package com.example.schedule.application.service;

import com.example.schedule.presentation.dto.WriterRequestDto;
import com.example.schedule.presentation.dto.WriterResponseDto;
import com.example.schedule.domain.entity.Writer;
import com.example.schedule.domain.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WriterServiceImpl implements WriterService{

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public WriterResponseDto saveWriter(WriterRequestDto dto) {
        Writer writer = new Writer(dto.getName(), dto.getEmail());

        return writerRepository.saveWriter(writer);
    }

    @Override
    public List<WriterResponseDto> findAllWriters() {
        return writerRepository.findAllWriters();
    }

    @Override
    public WriterResponseDto findWriterById(Long writerId) {
        Writer writer = writerRepository.findWriterByIdOrElseThrow(writerId);

        return new WriterResponseDto(writer);
    }

    @Override
    public WriterResponseDto updateWriter(Long writerId, String name, String email) {
        if (name == null || email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name and email are required values.");
        }

        int updatedRow = writerRepository.updateWriter(writerId, name, email);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + writerId);
        }

        Writer writer= writerRepository.findWriterByIdOrElseThrow(writerId);

        return new WriterResponseDto(writer);
    }

    @Override
    public void deleteWriter(Long writerId) {
        int deletedRow = writerRepository.deleteWriter(writerId);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + writerId);
        }
    }
}
