package de.app.shortener_service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shortLinks")
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService){
        this.shortLinkService = shortLinkService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUser(@PathVariable String shortUrl){
        String originalUrl = shortLinkService.redirectUrlString(shortUrl);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/workspace/{workspaceId}")
    public List <ShortLink> getShortlinks(@PathVariable String workspaceId){
        return shortLinkService.getShortlinksByWorkspaceId(workspaceId);
    }

    @PostMapping
    public ResponseEntity<String> generateLink(
            @RequestParam String originalUrl,
            @RequestParam(required = false) String workspaceId) {

        String shortUrl = shortLinkService.generateShortlink(originalUrl, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }
}
