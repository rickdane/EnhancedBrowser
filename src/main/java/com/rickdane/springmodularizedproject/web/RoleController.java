package com.rickdane.springmodularizedproject.web;

import com.rickdane.springmodularizedproject.domain.Role;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "roles", formBackingObject = Role.class)
@RequestMapping("/roles")
@Controller
@RooWebJson(jsonObject = Role.class)
public class RoleController {
}
