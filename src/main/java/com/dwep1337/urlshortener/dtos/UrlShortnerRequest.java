package com.dwep1337.urlshortener.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public record UrlShortnerRequest(
        @NotEmpty(message = "Destination URL cannot be empty")
        @URL(message = "Destination URL must be a valid URL") String destinationUrl) {
}
