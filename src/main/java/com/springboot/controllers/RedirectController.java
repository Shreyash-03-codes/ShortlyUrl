package com.springboot.controllers;

import com.springboot.services.redirect.RedirectUrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectController {

    @Autowired
    private RedirectUrlService redirectUrlService;

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws Exception{
        String longUrl=redirectUrlService.getRedirectUrl(shortUrl);

        if(longUrl.isEmpty()){
            response.sendError(HttpStatus.NOT_FOUND.value(), "URL not found");
        }
        System.out.println(longUrl);
        response.sendRedirect(longUrl);
    }
}
