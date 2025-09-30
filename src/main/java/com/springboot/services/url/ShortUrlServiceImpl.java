package com.springboot.services.url;

import com.springboot.dto.url.AllUrls;
import com.springboot.dto.url.LongUrl;
import com.springboot.dto.url.UrlRequestDto;
import com.springboot.dto.url.UrlResponseDto;
import com.springboot.entity.ShortUrl;
import com.springboot.entity.User;
import com.springboot.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlRepository urlRepository;

    private String generateUrl() {
        String chars = "ABCDEFGHIJKL12345MNOPQRSTUVWXYZabcdefghijkl06789mnopqrstuvwxyz";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        while (urlRepository.findByShortUrl(code.toString()).isPresent()) {
            code.setLength(0);
            for (int i = 0; i < 6; i++) {
                code.append(chars.charAt(random.nextInt(chars.length())));
            }
        }
        return code.toString();
    }

    @Transactional
    public UrlResponseDto getShortUrl(UrlRequestDto dto, User user) {
        String code = generateUrl();

        // Ensure long URL has protocol
        String longUrl = dto.getLongUrl();
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "http://" + longUrl;  // default to http
        }

        // Store only the short code in the DB
        ShortUrl shortUrl = new ShortUrl(code, longUrl, user);
        urlRepository.save(shortUrl);

        // Prepend domain dynamically when returning to client
        String fullShortUrl = "https://shortlyurl-6uvv.onrender.com/" + code;
        return new UrlResponseDto(fullShortUrl);
    }


    // In ShortUrlServiceImpl - RENAME this method to avoid conflict
    @Transactional
    public LongUrl getLongUrlPublic(String shortCode) { // Renamed from getLongUrl
        ShortUrl s = urlRepository.findByShortUrl(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        return new LongUrl(s.getLongUrl());
    }

    @Transactional
    public LongUrl getLongUrl(String shortCode) {
        ShortUrl s = urlRepository.findByShortUrl(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        return new LongUrl(s.getLongUrl());
    }

    @Transactional
    public List<AllUrls> getAllUrl(User user) {
        return urlRepository.findAllByUser(user)
                .stream()
                .map(u -> new AllUrls("https://shortlyurl-6uvv.onrender.com/" + u.getShortUrl(), u.getLongUrl()))
                .toList();
    }

    @Transactional
    public boolean deleteUrl(String shortCode, User user) {
        ShortUrl url = urlRepository.findByShortUrlAndUser(shortCode, user).orElse(null);
        if (url == null) return false;
        urlRepository.delete(url);
        return true;
    }
}
