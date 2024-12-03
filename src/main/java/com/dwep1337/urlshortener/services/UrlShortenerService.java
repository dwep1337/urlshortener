package com.dwep1337.urlshortener.services;

import com.dwep1337.urlshortener.dtos.UrlShortnerRequest;
import com.dwep1337.urlshortener.dtos.UrlShortnerResponse;
import com.dwep1337.urlshortener.models.UrlShortener;
import com.dwep1337.urlshortener.repository.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    @Value("${api.baseurl}")
    private String baseUrl;

    private static final int SLUG_LENGTH = 6;
    private static final int EXPIRATION_DAYS = 1;

    private final UrlShortenerRepository urlShortenerRepository;

    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository) {
        this.urlShortenerRepository = urlShortenerRepository;
    }

    public ResponseEntity<UrlShortnerResponse> createUrlShortener(UrlShortnerRequest request) {
        String uniqueSlug = generateUniqueSlug();
        saveUrl(request.destinationUrl(), uniqueSlug);

        String shortUrl = String.format("%s/r/%s", baseUrl, uniqueSlug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UrlShortnerResponse(shortUrl));
    }

    private String generateUniqueSlug() {
        String slug;
        do {
            slug = generateSlug(SLUG_LENGTH);
        } while (urlShortenerRepository.findByUrlSlug(slug).isPresent());
        return slug;
    }

    private String generateSlug(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    private void saveUrl(String destinationUrl, String slug) {
        UrlShortener urlShortener = UrlShortener.builder()
                .destinationUrl(destinationUrl)
                .urlSlug(slug)
                .expireAt(LocalDateTime.now().plusDays(EXPIRATION_DAYS))
                .build();
        urlShortenerRepository.save(urlShortener);
    }

    public ResponseEntity<UrlShortnerResponse> redirect(String slug) {
        Optional<UrlShortener> urlShortener = urlShortenerRepository.findByUrlSlug(slug);

        if (urlShortener.isPresent()) {
            UrlShortener foundUrl = urlShortener.get();
            if (foundUrl.getExpireAt().isAfter(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", foundUrl.getDestinationUrl())
                        .build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
