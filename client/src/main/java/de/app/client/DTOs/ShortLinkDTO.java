package de.app.client.DTOs;

public class ShortLinkDTO {
    private String originalUrl;
    private String shortUrl;
    private String workspaceId;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }


    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
}
