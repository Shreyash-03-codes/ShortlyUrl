package com.springboot.controllers;

import com.springboot.services.redirect.RedirectUrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    @Autowired
    private RedirectUrlService redirectUrlService;

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable("shortCode") String shortCode, HttpServletResponse response) throws Exception {
        try {
            // Fetch the long URL using only the short code
            String longUrl = redirectUrlService.getRedirectUrl(shortCode);

            // If long URL is null or empty, return 404
            if (longUrl == null || longUrl.isEmpty()) {
                response.sendError(HttpStatus.NOT_FOUND.value(), "URL not found");
                return;
            }

            // Redirect to the actual long URL
            response.sendRedirect(longUrl);

        } catch (Exception ex) {
            // Handle unexpected errors
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong: " + ex.getMessage());
        }
    }
}
