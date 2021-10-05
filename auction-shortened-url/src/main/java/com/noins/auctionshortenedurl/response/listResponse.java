package com.noins.auctionshortenedurl.response;

public class listResponse {
    private String originalUrl;
    private String shortLink;

    public listResponse(String originalUrl, String shortLink) {
        this.originalUrl = originalUrl;
        this.shortLink = shortLink;
    }

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

    @Override
    public String toString() {
        return "listResponse{" +
                "originalUrl='" + originalUrl + '\'' +
                ", shortLink='" + shortLink + '\'' +
                '}';
    }
}
