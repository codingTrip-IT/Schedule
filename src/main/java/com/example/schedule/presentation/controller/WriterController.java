package com.example.schedule.presentation.controller;

import com.example.schedule.presentation.dto.WriterRequestDto;
import com.example.schedule.presentation.dto.WriterResponseDto;
import com.example.schedule.application.service.WriterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/writers")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @PostMapping
    public ResponseEntity<WriterResponseDto> createWriter(@Valid @RequestBody WriterRequestDto dto){
        return new ResponseEntity<>(writerService.saveWriter(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WriterResponseDto>> findAllWriter() {
        return ResponseEntity.ok(writerService.findAllWriters());
    }

    @GetMapping("/{writerId}")
    public ResponseEntity<WriterResponseDto> findWriterById(@PathVariable("writerId") Long writerId){
        return ResponseEntity.ok(writerService.findWriterById(writerId));
    }

    @PatchMapping("/{writerId}")
    public ResponseEntity<WriterResponseDto> updateWriter(
            @PathVariable("writerId") Long writerId, @RequestBody WriterRequestDto dto){
        return new ResponseEntity<>(writerService.updateWriter(writerId,dto.getName(),dto.getEmail()),HttpStatus.OK);
    }

    @DeleteMapping("/{writerId}")
    public ResponseEntity<Void> deleteWriter(@PathVariable("writerId") Long writerId){
        writerService.deleteWriter(writerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
