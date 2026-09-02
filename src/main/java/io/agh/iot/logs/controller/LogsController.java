package io.agh.iot.logs.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    private final List<LogEntry> logs = new ArrayList<>();

    @GetMapping
    public List<LogEntry> getLogs() {
        int size = logs.size();
        return logs.subList(Math.max(0, size - 100), size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LogEntry addLog(@RequestBody LogRequest request) {
        LogEntry entry = new LogEntry(
            Instant.now().toString(),
            request.getLevel() != null ? request.getLevel() : "INFO",
            request.getMessage()
        );
        logs.add(entry);
        return entry;
    }

    @Data
    static class LogRequest {
        private String level;
        private String message;
    }

    record LogEntry(String timestamp, String level, String message) {}
}
