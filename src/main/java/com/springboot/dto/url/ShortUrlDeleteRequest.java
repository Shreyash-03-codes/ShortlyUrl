package com.springboot.dto.url;

public class ShortUrlDeleteRequest {
    private  String shortUrl;

    public ShortUrlDeleteRequest(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public ShortUrlDeleteRequest() {
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
