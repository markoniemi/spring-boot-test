package org.survey.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.service.user.UserService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2(topic = "api")
public class UsersController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/users", method = RequestMethod.GET)
    public ModelAndView users() {
        log.debug("GET:/user/users");
        ModelAndView model = new ModelAndView();
        model.setViewName("/user/users");
        model.addObject("users", userService.findAll());
        return model;
    }
}
