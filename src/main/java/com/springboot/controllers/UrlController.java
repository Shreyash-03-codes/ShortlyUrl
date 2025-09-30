package com.springboot.controllers;

import com.springboot.dto.url.*;
import com.springboot.entity.User;
import com.springboot.services.url.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/user/url")
public class UrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    // Helper method to create JSON error response
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("message", message);
        error.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(error, status);
    }

    @PostMapping("/getshort")
    public ResponseEntity<?> createShortUrl(@RequestBody UrlRequestDto urlRequestDto, @AuthenticationPrincipal User user) {
        try {
            UrlResponseDto dto = shortUrlService.getShortUrl(urlRequestDto, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return buildErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getlong")
    public ResponseEntity<?> getLongUrl( @RequestBody Original original) {
        try {
            // Call the method WITH user parameter
            LongUrl longUrlDto = shortUrlService.getLongUrl(original.getShortCode());

            if (longUrlDto == null) {
                return buildErrorResponse("Short URL not found for this user", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.status(HttpStatus.OK).body(longUrlDto);

        } catch (IllegalArgumentException ex) {
            return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return buildErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUrl(@RequestBody ShortUrlDeleteRequest shortUrl, @AuthenticationPrincipal User user) {
        try {
            // Pass the shortUrl string directly (service will extract code if needed)
            boolean deleted = shortUrlService.deleteUrl(shortUrl.getShortUrl(), user);

            if (!deleted) {
                return buildErrorResponse("Short URL not found or not authorized to delete", HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return buildErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal User user) {
        try {
            List<AllUrls> urls = shortUrlService.getAllUrl(user);
            return ResponseEntity.status(HttpStatus.OK).body(urls);
        } catch (Exception ex) {
            return buildErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
