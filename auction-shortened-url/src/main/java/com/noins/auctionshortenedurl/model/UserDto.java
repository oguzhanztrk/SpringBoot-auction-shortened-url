package com.noins.auctionshortenedurl.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long id;
    private String username;
    private List<Url> urlList = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(String username, List<Url> urlList) {
        this.username = username;
        this.urlList = urlList;
    }

   /* public static UserDto from(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        Collection<String> fields = user.getUrlList().stream().map(UrlDto::from).collect(Collectors.toList());
        return userDto;
     }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Url> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }
}
