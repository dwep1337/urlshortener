package com.dwep1337.urlshortener.controllers;

import com.dwep1337.urlshortener.dtos.UrlShortnerRequest;
import com.dwep1337.urlshortener.dtos.UrlShortnerResponse;
import com.dwep1337.urlshortener.services.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shortener")
    public ResponseEntity<UrlShortnerResponse> createUrlShortener(@RequestBody @Valid UrlShortnerRequest urlShortener) {
        return urlShortenerService.createUrlShortener(urlShortener);
    }

    @GetMapping("/r/{slug}")
    public ResponseEntity<UrlShortnerResponse> redirect(@PathVariable String slug) {
        return urlShortenerService.redirect(slug);
    }
}
