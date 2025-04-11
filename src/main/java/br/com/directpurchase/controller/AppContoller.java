package br.com.directpurchase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppContoller {

    @GetMapping(path = "/")
    public String welcome() {        
        return "🚀 API do EasyMerge está no ar!";
    }
}
