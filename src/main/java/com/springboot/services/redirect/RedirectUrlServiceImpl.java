package com.springboot.services.redirect;

import com.springboot.entity.ShortUrl;
import com.springboot.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedirectUrlServiceImpl implements RedirectUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    @Transactional(readOnly = true)
    public String getRedirectUrl(String shortCode) {
        try {
            System.out.println("Looking up short code: " + shortCode); // Debug

            // Extract just the code if full URL is passed
            if (shortCode.contains("/")) {
                shortCode = shortCode.substring(shortCode.lastIndexOf("/") + 1);
            }

            return urlRepository.findByShortUrl(shortCode)
                    .map(ShortUrl::getLongUrl)
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error in getRedirectUrl for: " + shortCode);
            e.printStackTrace();
            return null;
        }
    }
}