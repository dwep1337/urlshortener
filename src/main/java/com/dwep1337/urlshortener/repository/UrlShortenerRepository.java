package com.dwep1337.urlshortener.repository;

import com.dwep1337.urlshortener.models.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {
    Optional<UrlShortener> findByUrlSlug(String urlSlug);
}