package com.springboot.services.url;

import com.springboot.dto.url.AllUrls;
import com.springboot.dto.url.LongUrl;
import com.springboot.dto.url.UrlRequestDto;
import com.springboot.dto.url.UrlResponseDto;
import com.springboot.entity.User;
import java.util.List;

public interface ShortUrlService {

    UrlResponseDto getShortUrl(UrlRequestDto dto, User user);


    LongUrl getLongUrl(String shortCode); // Overloaded method for public access

    List<AllUrls> getAllUrl(User user);

    boolean deleteUrl(String shortCode, User user);
}