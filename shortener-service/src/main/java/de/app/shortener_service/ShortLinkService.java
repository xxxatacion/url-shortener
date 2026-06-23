package de.app.shortener_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ShortLinkService {
    @Value("${app.workspace.get-token-url}")
    private String workspaceApi;

    @Value("${app.stats.stats-url}")
    private String statsApi;

    private static final Logger log = LoggerFactory.getLogger(ShortLinkService.class);

    private final ShortLinkRepository shortLinkRepository;

    public ShortLinkService(ShortLinkRepository shortLinkRepository){
        this.shortLinkRepository = shortLinkRepository;
    }

    private boolean workspaceForShortUrlExists(ShortLink shortLink){
        return shortLink != null && shortLink.getWorkspaceId() != null;
    }

    public void addClickStatsService(ShortLink shortLink){
        if (workspaceForShortUrlExists(shortLink)){
            try {
                RestTemplate restTemplate = new RestTemplate();
                String statsApiUrl = statsApi + shortLink.getShortUrl();
                restTemplate.postForObject(statsApiUrl, null, Void.class);
            } catch (Exception e) {
                log.error("Stats-Service nicht erreichbar: {}", e.getMessage());
            }
        }
    }

    public String redirectUrlString(String shortUrl){
        ShortLink shortLink = shortLinkRepository.findByShortUrl(shortUrl);

        if (shortLink != null) {
            addClickStatsService(shortLink);
            return shortLink.getOriginalUrl();
        }
        log.error("ShortUrl ungültig");
        return null;
    }

    public List<ShortLink> getShortlinksByWorkspaceId(String workspaceId){
        return shortLinkRepository.findByWorkspaceId(workspaceId);
    }

    public ShortLink createShortLink(String originalUrl, String workspaceId) {

        if (originalUrl != null && !originalUrl.matches("(?i)^https?://.*")) {
            originalUrl = "https://" + originalUrl;
            log.debug("Protokoll fehlte. URL wurde angepasst auf: {}", originalUrl);
        }

        String randomShortUrl;
        do {
            randomShortUrl = UUID.randomUUID().toString().substring(0, 6);
        } while (shortLinkRepository.existsByShortUrl(randomShortUrl));

        ShortLink newLink = new ShortLink(originalUrl, randomShortUrl, workspaceId);

        String workspaceLog = (workspaceId != null) ? workspaceId : "Anonym";
        log.info("Erfolg: Neuer Shortlink '{}' erstellt (Workspace: {})", randomShortUrl, workspaceLog);

        return shortLinkRepository.save(newLink);
    }

    public boolean workspaceIdExists(String workspaceId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String workspaceApiUrl = workspaceApi + "check/" + workspaceId;

            Boolean exists = restTemplate.getForObject(workspaceApiUrl, Boolean.class);

            return Boolean.TRUE.equals(exists);

        } catch (Exception e) {
            log.error("Workspace-Service nicht erreichbar: {}", e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Der Workspace-Service ist momentan offline. Bitte versuche es später erneut."
            );
        }
    }

    public String generateShortlink(String originalLink, String workspaceId){
        if (workspaceId != null){
            if (workspaceIdExists(workspaceId)){
                return createShortLink(originalLink, workspaceId).getShortUrl();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Die angegebene Workspace-ID existiert nicht!"
                );
            }
        }
        return createShortLink(originalLink, null).getShortUrl();
    }
}