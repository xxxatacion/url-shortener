package de.app.client.DTOs;

import java.time.LocalDateTime;
public class StatsDTO {
    private String shortUrl;
    private LocalDateTime timestamp;

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}