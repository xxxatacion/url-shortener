package de.app.shortener_service;

import de.app.shortener_service.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {
    boolean existsByShortUrl(String shortUrl); // Prüfe ob Short-Url existiert
    ShortLink findByShortUrl(String shortUrl);
    List<ShortLink> findByWorkspaceId(String workspaceId);

}