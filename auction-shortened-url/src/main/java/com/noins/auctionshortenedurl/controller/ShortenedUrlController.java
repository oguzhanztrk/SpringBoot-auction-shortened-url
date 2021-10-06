package com.noins.auctionshortenedurl.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.noins.auctionshortenedurl.model.*;
import com.noins.auctionshortenedurl.repository.UrlRepository;
import com.noins.auctionshortenedurl.repository.UserFindRepository;
import com.noins.auctionshortenedurl.repository.UserRepository;
import com.noins.auctionshortenedurl.services.UrlService;
import com.noins.auctionshortenedurl.services.UserDetailsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping("/api/auth")

public class ShortenedUrlController {

    @Autowired
    private UrlService urlService;
    @Autowired
    private UserFindRepository userFindRepository;

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@Valid @RequestBody UrlDto urlDto,@RequestHeader(value="Authorization") String token)

    {
        String username=getUser(token);
        User user = userFindRepository.findByUsername(username);
        System.out.println(user.getId());

        Url urlIsAlreadySaved = urlService.getOriginalUrl(urlDto);
        System.out.println(urlIsAlreadySaved);
        if(urlIsAlreadySaved != null){
            UrlResponseDto urlResponseDto = getUrlResponseDto(urlIsAlreadySaved);
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }

        Url urlToRet = urlService.generateShortLink(urlDto,user);

        if(urlToRet != null )
        {
            UrlResponseDto urlResponseDto =getUrlResponseDto(urlToRet);
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }

        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("There was an error processing your request. please try again.");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);

    }

    @GetMapping("/link/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {

        if(StringUtils.isEmpty(shortLink))
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Invalid Url");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }
        Url urlToRet = urlService.getEncodedUrl("https://spring-auction-shortened-url.herokuapp.com/link/"+shortLink);

        if(urlToRet == null)
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }

        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
    }

    @GetMapping("/getUser")
    public List<User> getUser() {

        List<User> users = userDetailsService.getUser();

        return users;
    }
    private String getUser(String token){
        DecodedToken decodedToken = new DecodedToken();
        DecodedToken exp;
        exp=decodedToken.getDecoded(token);
        return exp.sub;
    }

    private UrlResponseDto getUrlResponseDto(Url urlIsAlreadySaved) {
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setOriginalUrl(urlIsAlreadySaved.getOriginalUrl());
        urlResponseDto.setShortLink(urlIsAlreadySaved.getShortLink());
        urlResponseDto.setUser(urlIsAlreadySaved.getUser());
        return urlResponseDto;
    }

}
