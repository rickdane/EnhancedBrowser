package com.rickdane.springmodularizedproject.module.userdata.domain;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emailtemplatebodys")
@Controller
@RooWebScaffold(path = "emailtemplatebodys", formBackingObject = EmailTemplateBody.class)
public class EmailTemplateBodyController {
}
