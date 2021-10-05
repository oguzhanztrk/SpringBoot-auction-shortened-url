package com.noins.auctionshortenedurl.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class DecodedToken {

    public String sub;
    public String iat;
    public String exp;

    public DecodedToken getDecoded(String encodedToken)  {
        String[] pieces = encodedToken.split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), StandardCharsets.UTF_8);

        return new Gson().fromJson(jsonString, DecodedToken.class);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
