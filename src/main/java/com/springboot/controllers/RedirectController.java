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
    public void redirect(@PathVariable("shortCode") String shortCode, HttpServletResponse response) {
        try {
            String longUrl = redirectUrlService.getRedirectUrl(shortCode);

            if (longUrl == null || longUrl.isEmpty()) {
                response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found");
                return;
            }

            // Ensure protocol is present (optional safety)
            if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
                longUrl = "http://" + longUrl;
            }

            response.sendRedirect(longUrl);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
