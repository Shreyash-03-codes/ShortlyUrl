package com.springboot.dto.url;

public class Original {
    private String shortCode;

    public Original() {}

    public Original(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }


    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
