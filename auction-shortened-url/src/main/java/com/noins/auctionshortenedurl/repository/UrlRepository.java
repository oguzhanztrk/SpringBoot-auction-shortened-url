package com.noins.auctionshortenedurl.repository;

import com.noins.auctionshortenedurl.model.Url;
import com.noins.auctionshortenedurl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long>
{

    public Url findByUserId(Long id);
    public Url findByOriginalUrl(String originalUrl);

    public Url findByShortLink(String shortLink);
}
