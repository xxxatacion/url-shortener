package de.app.shortener_service;

import jakarta.persistence.*;

@Entity
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Java-Standard: camelCase nutzen!
    private String originalUrl;
    @Column(unique = true)
    private String shortUrl;
    private String workspaceId;

    public ShortLink() {
    }

    public ShortLink(String originalUrl, String shortUrl, String workspaceId) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.workspaceId = workspaceId;
    }

    // Getter und Setter (natürlich auch angepasst auf camelCase)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }
}