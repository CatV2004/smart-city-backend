package com.smartcity.urban_management.modules.location.service;

import com.smartcity.urban_management.modules.location.dto.ReverseGeocodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeocodingService {

    private static final String NOMINATIM_URL =
            "https://nominatim.openstreetmap.org/reverse";

    private final RestTemplate restTemplate;
    @Value("${geocode.user-agent}")
    private String userAgent;

    @Cacheable(value = "geocode", key = "#lat + ':' + #lng")
    public ReverseGeocodeResponse reverse(double lat, double lng) {

        String url = UriComponentsBuilder
                .fromHttpUrl(NOMINATIM_URL)
                .queryParam("format", "jsonv2")
                .queryParam("lat", lat)
                .queryParam("lon", lng)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,userAgent);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT_LANGUAGE, "vi");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("display_name")) {
                log.warn("Reverse geocode returned empty for {},{}", lat, lng);
                return new ReverseGeocodeResponse("Không xác định");
            }

            String address = (String) body.get("display_name");

            return new ReverseGeocodeResponse(address);

        } catch (RestClientException ex) {

            log.error("Reverse geocode failed for {},{} - {}",
                    lat, lng, ex.getMessage());

            return new ReverseGeocodeResponse("Không thể lấy địa chỉ");
        }
    }
}