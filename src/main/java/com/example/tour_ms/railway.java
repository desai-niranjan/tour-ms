package com.example.tour_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class railway {

    @GetMapping("/railway")
    public String getData() {

        return  "Please book your railway/train tickets from tour app on 12% discount" ;
    }

}
