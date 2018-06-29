package org.dreamcafe.smjc.controller;

import org.dreamcafe.smjc.domain.GreetingInfo;
import org.dreamcafe.smjc.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {
    @Autowired
    private WelcomeService welcomeService;

    @GetMapping(path = "/{name}")
    public String greeting(@PathVariable(name = "name") final String name, final ModelMap modelMap) {
        GreetingInfo info = welcomeService.greeting(name);
        modelMap.addAttribute("info", info);

        return "templates/welcome";
    }
}
