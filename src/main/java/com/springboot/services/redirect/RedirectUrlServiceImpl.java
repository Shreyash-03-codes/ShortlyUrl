package com.springboot.services.redirect;

import com.springboot.entity.ShortUrl;
import com.springboot.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedirectUrlServiceImpl implements RedirectUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public String getRedirectUrl(String shortCode) {
        // Fetch the ShortUrl entity using only the short code
        return urlRepository.findByShortUrl(shortCode)
                .map(ShortUrl::getLongUrl)
                .orElse(null); // return null if not found
    }
}
