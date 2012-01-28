package com.rickdane.springmodularizedproject.web;

import com.rickdane.springmodularizedproject.domain.UserRole;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "userroles", formBackingObject = UserRole.class)
@RequestMapping("/userroles")
@Controller
@RooWebJson(jsonObject = UserRole.class)
public class UserRoleController {
}
