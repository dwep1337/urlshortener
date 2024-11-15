package com.dwep1337.urlshortener.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "url_shorteners")
@Table(name = "url_shorteners")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationUrl;

    @Column(unique = true)
    private String urlSlug;

    private LocalDateTime expireAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
