package com.timetrackerbackend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    @GetMapping
    public String getIndex() {
        return "Hej";
    }
}
