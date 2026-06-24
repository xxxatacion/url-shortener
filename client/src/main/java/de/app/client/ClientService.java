package de.app.client;

import de.app.client.DTOs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Value("${app.workspace.get-token-url}")
    private String workspaceApi;

    @Value("${app.shortener.internal-url}")
    private String shortenerApi;

    @Value("${app.stats.stats-url}")
    private String statsApi;
    // Workspace
    public String takeNewTokenFromWorkspace(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            WorkspaceDTO newWorkspace = restTemplate.postForObject(workspaceApi, null, WorkspaceDTO.class);
            log.info("Erfolgreich neuen Token vom Workspace-Service geholt: {}", newWorkspace.getToken());
            return newWorkspace.getToken();
        } catch (RestClientException e) {
            log.error("Fehler beim Generieren eines Tokens: Workspace-Service nicht erreichbar! ({})", e.getMessage());
            throw new RuntimeException("Workspace-Service ist offline.");
        }
    }

    public boolean tokenExists(String token){
        try {
            RestTemplate restTemplate = new RestTemplate();
            Boolean exists = restTemplate.getForObject(workspaceApi + "/check/" + token, Boolean.class);
            return Boolean.TRUE.equals(exists);
        } catch (RestClientException e) {
            log.error("Fehler bei Token-Prüfung: Workspace-Service nicht erreichbar! ({})", e.getMessage());
            throw new RuntimeException("Workspace-Service ist offline.");
        }
    }

    // Shortener
    public String takeNewShortLinkFromShortener(String originalUrl, String workspaceId) {
        log.info("takeNewShortLinkFromShortener Funktion wurde aufgerufen");
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(shortenerApi)
                    .queryParam("originalUrl", originalUrl);

            if (workspaceId != null && !workspaceId.isEmpty()) {
                builder.queryParam("workspaceId", workspaceId);
            }
            String shortUrl = restTemplate.postForObject(builder.toUriString(), null, String.class);
            log.info("Erfolgreich neuen Shortlink generiert: {}", shortUrl);
            return shortUrl;

        } catch (HttpClientErrorException e) {
            log.warn("Backend meldet Validierungsfehler: {}", e.getStatusCode());
            throw new IllegalArgumentException("Die angegebene Workspace-ID existiert nicht!");

        } catch (RestClientException e) {
            log.error("Fehler beim Kürzen: Shortener-Service nicht erreichbar! ({})", e.getMessage());
            throw new RuntimeException("Shortener-Service ist offline.");
        }
    }

    public List<ShortLinkDTO> getAllShortLinksFromWorkspace(String workspaceId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            ShortLinkDTO[] shortUrlsArray = restTemplate.getForObject(shortenerApi + "/workspace/" + workspaceId, ShortLinkDTO[].class);
            if (shortUrlsArray != null){
                return Arrays.asList(shortUrlsArray);
            }
        } catch (RestClientException e) {
            log.error("Fehler beim Abrufen der Links: Shortener-Service ist offline!");
        }
        return new ArrayList<>();
    }

    // Stats
    public List<StatsDTO> getStatsFromShortUrl(String shortUrl){
        try {
            RestTemplate restTemplate = new RestTemplate();
            StatsDTO[] statsArray = restTemplate.getForObject(statsApi + "/"  + shortUrl, StatsDTO[].class);
            if (statsArray != null) {
                return Arrays.asList(statsArray);
            }
        } catch (RestClientException e) {
            log.error("Fehler beim Abrufen der Klicks für {}: Stats-Service ist offline!", shortUrl);
        }
        return new ArrayList<>();
    }

    // Dashboard
    public DashboardDTO getDashboard(String token){
        List<ShortLinkDTO> shortLinkDTOs = getAllShortLinksFromWorkspace(token);
        List<StatsShortUrl> statsShortUrls = new ArrayList<>();

        for (ShortLinkDTO sld : shortLinkDTOs) {
            List<StatsDTO> statsDTOs = getStatsFromShortUrl(sld.getShortUrl());

            StatsShortUrl statsShortUrl = new StatsShortUrl();
            statsShortUrl.setShortLinkDTO(sld);
            statsShortUrl.setStatsDTOS(statsDTOs);

            statsShortUrls.add(statsShortUrl);
        }

        log.info("Dashboard-Daten für Token {} aggregiert ({} Links geladen).", token, statsShortUrls.size());
        return new DashboardDTO(statsShortUrls);
    }
}
