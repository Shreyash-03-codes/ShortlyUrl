package com.springboot.controllers;

import com.springboot.services.redirect.RedirectUrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class RedirectController {

    @Autowired
    private RedirectUrlService redirectUrlService;

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable("shortCode") String shortCode, HttpServletResponse response) throws IOException {
        try {
            System.out.println("Redirect attempt for short code: " + shortCode); // Debug log

            String longUrl = redirectUrlService.getRedirectUrl(shortCode);

            if (longUrl == null || longUrl.trim().isEmpty()) {
                System.out.println("Short URL not found: " + shortCode);
                response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found");
                return;
            }

            System.out.println("Redirecting to: " + longUrl); // Debug log

            // Ensure protocol is present
            if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
                longUrl = "https://" + longUrl;
            }

            response.sendRedirect(longUrl);

        } catch (Exception e) {
            System.err.println("Error in redirect for short code: " + shortCode);
            e.printStackTrace();
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Redirect error occurred");
        }
    }
}