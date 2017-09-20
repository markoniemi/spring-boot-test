package org.survey.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private MessageSource messageSource;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() != null) {
            userService.update(user);
        } else {
            userService.create(user);
        }
        return "redirect:/user/users";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public ModelAndView newUser() {
        return editUser(null);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable String username) {
        User user = userService.findOne(username);
        log.debug("editUser() - found user: " + user);
        if (user == null) {
            user = new User();
        }
        ModelAndView model = new ModelAndView();
        model.setViewName("/user/user");
        model.addObject("user", user);
        model.addObject("roles", getRolesAsMap());
        return model;
    }

    @RequestMapping(value = "/user/delete/{username}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable String username) {
        userService.delete(username);
        return "redirect:/user/users";
    }

    private Map<String, String> getRolesAsMap() {
        Map<String, String> roleMap = new HashMap<String, String>();
        Role[] roles = Role.values();
        for (Role role : roles) {
            roleMap.put(role.name(), messageSource.getMessage(role.name(), null, null));
        }
        return roleMap;
    }
}
