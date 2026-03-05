package com.smartcity.urban_management.modules.location.controller;

import com.smartcity.urban_management.modules.location.dto.ReverseGeocodeResponse;
import com.smartcity.urban_management.modules.location.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geocode")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingService geocodingService;

    @GetMapping("/reverse")
    public ReverseGeocodeResponse reverse(
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        return geocodingService.reverse(lat, lng);
    }
}