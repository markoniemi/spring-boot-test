package org.survey.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.survey.controller.UserValidator;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.service.user.UserService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2(topic = "api")
public class UserController {
    @Autowired
    private MessageSource messageSource;
    @Resource
    private UserService userService;
    @Resource
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute @Validated User user, BindingResult bindingResult) {
        log.debug("POST:/user/save: {}", user);
        if (bindingResult.hasErrors()) {
            return "/user/user";
        }
        try {
            if (user.getId() != null) {
                userService.update(user);
            } else {
                userService.create(user);
            }
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("user", e.getMessage()));
        }
        if (bindingResult.hasErrors()) {
            return "/user/user";
        }
        return "redirect:/user/users";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public ModelAndView newUser() {
        log.debug("GET:/user/new");
        return editUser(null);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable String username) {
        log.debug("GET:/user/{}",username);
        User user = userService.findByUsername(username);
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
        log.debug("DELETE:/user/delete/{}",username);
        userService.delete(userService.findByUsername(username).getId());
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
