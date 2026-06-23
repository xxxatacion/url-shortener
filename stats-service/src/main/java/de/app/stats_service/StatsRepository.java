package de.app.stats_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {
    List<Stats> findByShortUrl(String shortUrl);
    //int countByShortUrlAndTimestampAfter(String shortUrl, LocalDateTime date); // Klicks zusammenzählen
    // Service: LocalDateTime.now().minusDays(7) oder 30.. je nachdem ...



}
