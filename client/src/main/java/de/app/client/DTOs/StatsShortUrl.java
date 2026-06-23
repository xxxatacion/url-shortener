package de.app.client.DTOs;

import java.util.List;

public class StatsShortUrl {
    private ShortLinkDTO shortLinkDTO;
    private List<StatsDTO> statsDTOS;

    public StatsShortUrl(ShortLinkDTO shortLinkDTO, List<StatsDTO> statsDTOS){
        this.shortLinkDTO = shortLinkDTO;
        this.statsDTOS = statsDTOS;
    }

    public StatsShortUrl(ShortLinkDTO shortLinkDTO){
        this.shortLinkDTO = shortLinkDTO;
    }

    public StatsShortUrl() {

    }

    public ShortLinkDTO getShortLinkDTO() {
        return shortLinkDTO;
    }

    public void setShortLinkDTO(ShortLinkDTO shortLinkDTO) {
        this.shortLinkDTO = shortLinkDTO;
    }

    public List<StatsDTO> getStatsDTOS() {
        return statsDTOS;
    }

    public void setStatsDTOS(List<StatsDTO> statsDTOS) {
        this.statsDTOS = statsDTOS;
    }
}
