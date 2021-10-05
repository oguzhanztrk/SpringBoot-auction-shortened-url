package com.noins.auctionshortenedurl.model;

public class UrlDto
{
    private String url;

    public UrlDto(String url) {
        this.url = url;
    }

    public UrlDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "url='" + url + '\'' +
                '}';
    }
}
