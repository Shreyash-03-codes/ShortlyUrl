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
import java.util.Optional;
import java.util.Random;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private UrlRepository urlRepository;

    private String generateUrl(){
        String chars = "ABCDEFGHIJKL12345MNOPQRSTUVWXYZabcdefghijkl06789mnopqrstuvwxyz";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for(int i=0; i<6; i++){
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        while (urlRepository.findByShortUrl(code.toString()).isPresent()){
            code.setLength(0);
            for(int i=0; i<6; i++){
                code.append(chars.charAt(random.nextInt(chars.length())));
            }
        }
        return code.toString();
    }

    public UrlResponseDto getShortUrl(UrlRequestDto dto, User user){
        try {
            String code = generateUrl();
            ShortUrl shortUrl = new ShortUrl("https://shortlyurl-6uvv.onrender.com"+code, dto.getLongUrl(), user);
            urlRepository.save(shortUrl);
            return new UrlResponseDto(shortUrl.getShortUrl());
        } catch (Exception ex) {
            throw new RuntimeException("Error while generating short URL: " + ex.getMessage());
        }
    }

    public LongUrl getLongUrl(String shortUrl) {
        try {
            Optional<ShortUrl> s = urlRepository.findByShortUrl(shortUrl);
            if(s.isPresent()){
                return new LongUrl(s.get().getLongUrl());
            }
            throw new RuntimeException("Short URL not found");
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching long URL: " + ex.getMessage());
        }
    }

    @Override
    public LongUrl getLongUrl(String shortUrl, User user) {
        try {
            Optional<ShortUrl> s = urlRepository.findByShortUrlAndUser(shortUrl, user);
            if(s.isPresent()){
                return new LongUrl(s.get().getLongUrl());
            }
            throw new RuntimeException("Short URL not found for this user");
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching long URL: " + ex.getMessage());
        }
    }

    public List<AllUrls> getAllUrl(User user){
        try {
            return urlRepository.findAllByUser(user)
                    .stream()
                    .map(u -> new AllUrls(u.getShortUrl(), u.getLongUrl()))
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching all URLs: " + ex.getMessage());
        }
    }

    public boolean deleteUrl(String shortUrl, User user){
        try {
            Optional<ShortUrl> url = this.urlRepository.findByShortUrlAndUser(shortUrl, user);
            System.out.println(url);
            if(url.isPresent() && url.get().getUser().getId() == user.getId()){
                urlRepository.delete(url.get());
                return true;
            }
            throw new RuntimeException("Short URL not found or not authorized to delete");
        } catch (Exception ex) {
            throw new RuntimeException("Error while deleting URL: " + ex.getMessage());
        }
    }
}
