package br.com.directpurchase.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppContoller {

    @GetMapping(path = "/")
    public String welcome() {
        String msg = "🚀 API do EasyMerge está no ar!";
        String html = "<!DOCTYPE html><html><head><title>EasyMerge - API</title></head><body><h1>" + msg
                + "</h1></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html).getBody();
    }
}
