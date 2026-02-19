package com.followba.store.admin.controller;

import com.followba.store.common.resp.Out;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    public Out<String> hello() {
        return  Out.success("ok");
    }

    @GetMapping("/health/status")
    public String health() {
        return  "200";
    }
}
