package com.springboot.dto.url;

public class UrlRequestDto {

    private String longUrl;

    public UrlRequestDto(String longUrl) {
        this.longUrl = longUrl;
    }

    public UrlRequestDto() {
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
