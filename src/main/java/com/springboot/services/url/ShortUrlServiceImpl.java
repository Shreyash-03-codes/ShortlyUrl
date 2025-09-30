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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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

    @Override
    @Transactional
    public UrlResponseDto getShortUrl(UrlRequestDto dto, User user) {
        String code = generateUrl();

        String longUrl = dto.getLongUrl();
        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "http://" + longUrl;
        }

        ShortUrl shortUrl = new ShortUrl(code, longUrl, user);
        urlRepository.save(shortUrl);

        String fullShortUrl = "https://shortlyurl-6uvv.onrender.com/" + code;
        return new UrlResponseDto(fullShortUrl);
    }



    @Override
    @Transactional
    public LongUrl getLongUrl(String shortCode) {
        String code = extractShortCode(shortCode);
        ShortUrl s = urlRepository.findByShortUrl(code)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        return new LongUrl(s.getLongUrl());
    }

    @Override
    @Transactional
    public List<AllUrls> getAllUrl(User user) {
        return urlRepository.findAllByUser(user)
                .stream()
                .map(u -> new AllUrls("https://shortlyurl-6uvv.onrender.com/" + u.getShortUrl(), u.getLongUrl()))
                .toList();
    }

    @Override
    @Transactional
    public boolean deleteUrl(String shortUrl, User user) {
        String code = extractShortCode(shortUrl);
        ShortUrl url = urlRepository.findByShortUrlAndUser(code, user).orElse(null);
        if (url == null) return false;
        urlRepository.delete(url);
        return true;
    }

    // Helper method to extract short code from full URL
    private String extractShortCode(String input) {
        if (input.contains("/")) {
            return input.substring(input.lastIndexOf("/") + 1);
        }
        return input;
    }
}