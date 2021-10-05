package com.noins.auctionshortenedurl.model;

import java.util.List;

public class UrlResponseDto {

    private String originalUrl;
    private String shortLink;
    private User User;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public com.noins.auctionshortenedurl.model.User getUser() {
        return User;
    }

    public void setUser(com.noins.auctionshortenedurl.model.User user) {
        User = user;
    }

    @Override
    public String toString() {
        return "UrlResponseDto{" +
                "originalUrl='" + originalUrl + '\'' +
                ", shortLink='" + shortLink + '\'' +
                ", User=" + User +
                '}';
    }
}
