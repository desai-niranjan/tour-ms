package com.example.tour_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class flight {

    @GetMapping("/flight")
    public String getData() {

        return  "Please book your flight tickets from tour app on 15% discount" ;
    }
}
