package org.dreamcafe.smjc.controller;

import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/welcome")
public class WelcomeRestController {
    @Autowired
    private WelcomeService welcomeService;

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GreetingInfo> greeting(@PathVariable(name = "name") final String name) {
        GreetingInfo info = welcomeService.greeting(name);
        return ResponseEntity.ok(info);
    }
}
