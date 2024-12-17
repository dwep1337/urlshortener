package com.dwep1337.urlshortener.services;

import com.dwep1337.urlshortener.dtos.UrlShortnerRequest;
import com.dwep1337.urlshortener.dtos.UrlShortnerResponse;
import com.dwep1337.urlshortener.models.UrlShortener;
import com.dwep1337.urlshortener.repository.UrlShortenerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class UrlShortenerServiceTest {

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @Mock
    private UrlShortenerRepository urlShortenerRepository;

    @Value("${api.baseurl}")
    private String baseUrl;

    @Test
    void shouldCreateUrlShortener() {

        ReflectionTestUtils.setField(urlShortenerService, "baseUrl", baseUrl);

        Mockito.when(urlShortenerRepository.findByUrlSlug(Mockito.anyString()))
                .thenReturn(Optional.empty());

        UrlShortnerRequest request = new UrlShortnerRequest("https://google.com");
        ResponseEntity<UrlShortnerResponse> response = urlShortenerService.createUrlShortener(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertThat(response.getBody().shortUrl()).startsWith(baseUrl + "/r/");

    }

    @Test
    void shouldRedirectToDestinationUrl() {

        UrlShortener urlShortenerMock = UrlShortener.builder()
                .destinationUrl("https://example.com")
                .urlSlug("a7d8g9")
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();


        Mockito.when(urlShortenerRepository.findByUrlSlug("a7d8g9"))
                .thenReturn(Optional.of(urlShortenerMock));

        ResponseEntity<UrlShortnerResponse> response = urlShortenerService.redirect("a7d8g9");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(Objects.requireNonNull(response.getHeaders().getLocation()).toString()).isEqualTo("https://example.com");

    }

    @Test
    void shouldReturnNotFoundForExpiredUrl(){

        UrlShortener urlShortenerMock = UrlShortener.builder()
                .destinationUrl("https://example.com")
                .urlSlug("a7d8g9")
                .expireAt(LocalDateTime.now().minusDays(1))
                .build();

        Mockito.when(urlShortenerRepository.findByUrlSlug("a7d8g9"))
                .thenReturn(Optional.of(urlShortenerMock));


        ResponseEntity<UrlShortnerResponse> response = urlShortenerService.redirect("a7d8g9");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}