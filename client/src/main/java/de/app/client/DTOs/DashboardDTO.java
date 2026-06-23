package de.app.client.DTOs;

import java.util.List;

public class DashboardDTO {
    private List<StatsShortUrl> statsShortUrls;

    public DashboardDTO(List<StatsShortUrl> statsShortUrls) {
        this.statsShortUrls = statsShortUrls;
    }

    public List<StatsShortUrl> getStatsShortUrls() {
        return statsShortUrls;
    }

    public void setStatsShortUrls(List<StatsShortUrl> statsShortUrls) {
        this.statsShortUrls = statsShortUrls;
    }
}
