package com.springboot.services.redirect;

import com.springboot.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RedirectUrlServiceImpl implements RedirectUrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public String getRedirectUrl(String shortCode) {

        String s= urlRepository.findByShortUrl("http://localhost:8080/"+shortCode).get().getLongUrl();
        return s;
    }
}
