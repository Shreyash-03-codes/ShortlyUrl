package com.springboot.dto.url;

public class UrlResponseDto {

    private String shortUrl;

    public UrlResponseDto(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public UrlResponseDto() {
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
