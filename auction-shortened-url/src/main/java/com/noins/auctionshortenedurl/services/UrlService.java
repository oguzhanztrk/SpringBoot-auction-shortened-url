package com.noins.auctionshortenedurl.services;

import com.noins.auctionshortenedurl.model.Url;
import com.noins.auctionshortenedurl.model.UrlDto;
import com.noins.auctionshortenedurl.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UrlService
{
    public Url generateShortLink(UrlDto urlDto, User user);
    public Url persistShortLink(Url url);
    public Url getEncodedUrl(String url);
    public Url getOriginalUrl(UrlDto url);
    public  void  deleteShortLink(Url url);
}
