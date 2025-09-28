package com.springboot.services.url;

import com.springboot.dto.url.AllUrls;
import com.springboot.dto.url.LongUrl;
import com.springboot.dto.url.UrlRequestDto;
import com.springboot.dto.url.UrlResponseDto;
import com.springboot.entity.User;

import java.util.List;
import java.util.Optional;

public interface ShortUrlService {

    public UrlResponseDto getShortUrl(UrlRequestDto dto, User user);
    LongUrl  getLongUrl(String shortUrl);
    LongUrl  getLongUrl(String shortUrl,User user);
    List<AllUrls> getAllUrl(User user);
    boolean deleteUrl(String shortUrl,User user);
}
