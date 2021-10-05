package com.noins.auctionshortenedurl.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.noins.auctionshortenedurl.model.*;
import com.noins.auctionshortenedurl.repository.UrlRepository;
import com.noins.auctionshortenedurl.repository.UserFindRepository;
import com.noins.auctionshortenedurl.repository.UserRepository;
import com.noins.auctionshortenedurl.services.UrlService;
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

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@Valid @RequestBody UrlDto urlDto,@RequestHeader(value="Authorization") String token)

    {
       /*String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));



        System.out.println(token);
        System.out.println(payload);*/


        String username=getUser(token);
        //System.out.println(username);
        //user1 = userRepository.getById((long) 5);
        //Optional<User> optionalUser =userRepository.findByUsername(username);
       // optionalUser.
        User user = userFindRepository.findByUsername(username);
        System.out.println(user.getId());


        Url urlIsAlreadySaved = urlService.getOriginalUrl(urlDto);
        System.out.println(urlIsAlreadySaved);
        if(urlIsAlreadySaved != null){
            UrlResponseDto urlResponseDto = getUrlResponseDto(urlIsAlreadySaved);
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
           /* UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("404");
            urlErrorResponseDto.setError("The entered url is already registered.");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }*/
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
        Url urlToRet = urlService.getEncodedUrl("http://localhost:8080/link/"+shortLink);

        if(urlToRet == null)
        {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }

        /*if(urlToRet.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            urlService.deleteShortLink(urlToRet);
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
            urlErrorResponseDto.setStatus("200");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto,HttpStatus.OK);
        }*/

        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
    }
   /* @GetMapping("/list")
    private ResponseEntity<List<Url>> listrecord(@RequestHeader(value="Authorization") String token){

        String username = getUser(token);
        User user = userFindRepository.findByUsername(username);

        return ResponseEntity.ok(urlService.getUserData(user.getId()));

    }*/
    /* listresponse oluştur

               getuser(token) tokeni decode etmek için kullan
               User user = userFindRepository.findByUsername(username);
               user.getid id yi al
               url modelinde id ile eşleşenleri listele


     */

    @GetMapping("/getUrl")
    public Optional<Url> getUserField(@RequestHeader(value="Authorization") String token) {
        String username = getUser(token);
        User user = userFindRepository.findByUsername(username);
        Optional optUser = userRepository.findById(user.getId());
        return urlRepository.findById(user.getId());
           }

    /*@GetMapping("/getUser")
    List<User> getUser() {
        return userRepository.findAll();
    }*/
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
