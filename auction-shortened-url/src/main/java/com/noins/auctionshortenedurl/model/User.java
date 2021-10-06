package com.noins.auctionshortenedurl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "url_id")
    private List<Url> urlList = new ArrayList<>();



    public User(){

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }






    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Url> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
