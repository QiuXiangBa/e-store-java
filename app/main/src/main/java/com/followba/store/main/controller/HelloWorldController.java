package com.followba.store.main.controller;

import com.followba.store.common.resp.Out;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public Out<String> helloWorld(){
        return Out.success("Hello World!");
    }
}
