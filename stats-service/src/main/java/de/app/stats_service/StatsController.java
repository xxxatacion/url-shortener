package de.app.stats_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stats") // Hier der Slash!
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/{shortUrl}")
    public ResponseEntity<Void> addClick(@PathVariable String shortUrl){
        statsService.addClick(shortUrl);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{shortUrl}")
    public List<Stats> getStats(@PathVariable String shortUrl){
        return statsService.getStatsByShortUrl(shortUrl);
    }
}