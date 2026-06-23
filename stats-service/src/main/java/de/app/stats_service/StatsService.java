package de.app.stats_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsService {

    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    private final StatsRepository statsRepository;

    public StatsService(StatsRepository statsRepository){
        this.statsRepository = statsRepository;
    }

    public void addClick(String shortUrl){
        log.info("Klick registriert! URL: '{}' um {}", shortUrl, LocalDateTime.now());

        Stats stats = new Stats(shortUrl, LocalDateTime.now());
        statsRepository.save(stats);
    }

    public List<Stats> getStatsByShortUrl(String shortUrl){
        return statsRepository.findByShortUrl(shortUrl);
    }
}
