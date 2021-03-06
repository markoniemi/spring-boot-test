package org.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2(topic = "api")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home() {
        log.debug("GET:/login");
        return "/login";
    }
}
