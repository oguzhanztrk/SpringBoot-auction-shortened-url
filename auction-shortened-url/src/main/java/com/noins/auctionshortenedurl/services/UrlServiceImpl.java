package com.noins.auctionshortenedurl.services;

import com.google.common.hash.Hashing;
import com.noins.auctionshortenedurl.model.Url;
import com.noins.auctionshortenedurl.model.UrlDto;
import com.noins.auctionshortenedurl.model.User;
import com.noins.auctionshortenedurl.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;
    @Override
    public Url generateShortLink(UrlDto urlDto, User user) {
        if(StringUtils.isNotEmpty(urlDto.getUrl()))
        {
             List<String> urlList;

            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url urlToPersist = new Url();
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setUser(user);
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            Url urlToRet = persistShortLink(urlToPersist);

            if(urlToRet != null)
                return urlToRet;

            return null;
        }
        return null;
    }

    private String encodeUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        encodedUrl = "http://localhost:8080/link/" +encodedUrl;
        return  encodedUrl;
    }

    @Override
    public Url persistShortLink(Url url) {
        Url urlToRet = urlRepository.save(url);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String url) {
        Url urlToRet = urlRepository.findByShortLink(url);
        return urlToRet;
    }

    @Override
    public Url getOriginalUrl(UrlDto url) {
        return urlRepository.findByOriginalUrl(url.getUrl());
    }


    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);

    }
}
