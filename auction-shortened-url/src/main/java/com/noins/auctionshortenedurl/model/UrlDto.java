package com.noins.auctionshortenedurl.model;

import lombok.Data;

import java.util.Objects;
import java.util.stream.Collectors;
@Data
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
