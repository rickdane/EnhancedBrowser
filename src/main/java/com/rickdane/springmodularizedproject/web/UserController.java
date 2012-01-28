package com.rickdane.springmodularizedproject.web;

import com.rickdane.springmodularizedproject.domain.User;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "users", formBackingObject = User.class)
@RequestMapping("/users")
@Controller
@RooWebJson(jsonObject = User.class)
public class UserController {

    @Autowired
    private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "users/create";
        }
        if (user.getId() != null) {
            User savedUser = User.findUser(user.getId());
            if (!savedUser.getPassword().equals(user.getPassword())) {
                user.setPassword(messageDigestPasswordEncoder.encodePassword(user.getPassword(), null));
            }
        } else {
            user.setPassword(messageDigestPasswordEncoder.encodePassword(user.getPassword(), null));
        }
        user.persist();
        return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), request);
    }

    @RequestMapping(value = "/loggedIn", produces = "text/html")
    public String loggedIn() {
        return "loggedIn";
    }
}
