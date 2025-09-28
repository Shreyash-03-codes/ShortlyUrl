package com.springboot.dto.url;

public class LongUrl {
    private String longUrl;

    public LongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LongUrl() {
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
