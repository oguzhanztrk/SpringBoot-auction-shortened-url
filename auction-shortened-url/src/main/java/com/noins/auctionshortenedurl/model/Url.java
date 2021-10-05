package com.noins.auctionshortenedurl.model;

import com.noins.auctionshortenedurl.repository.UserRepository;

import javax.persistence.*;

@Entity
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String originalUrl;
    private String shortLink;
    @ManyToOne
    private User user;

    public Url(String originalUrl, String shortLink) {

        this.originalUrl = originalUrl;
        this.shortLink = shortLink;
    }

    public Url() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
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

    public void setShortLink(String shortenedUrl) {
        this.shortLink = shortenedUrl;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortenedUrl='" + shortLink + '\'' +
                ", user=" + user +
                '}';
    }


}
