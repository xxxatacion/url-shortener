package de.app.stats_service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Hier ist jetzt long!

    private String shortUrl;
    private LocalDateTime timestamp;

    public Stats() {}

    public Stats(String shortUrl, LocalDateTime timestamp) {
        this.shortUrl = shortUrl;
        this.timestamp = timestamp;
    }

    public long getId() { return id; } // Getter auf long angepasst
    public void setId(long id) { this.id = id; } // Setter auf long angepasst

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}