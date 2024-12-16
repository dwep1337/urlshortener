package com.dwep1337.urlshortener.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlShortnerRequest {
        @URL(message = "Destination URL must be a valid URL")
        @NotEmpty(message = "Destination URL cannot be empty")
        private String destinationUrl;
}
