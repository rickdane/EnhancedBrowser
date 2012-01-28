package com.rickdane.springmodularizedproject.module.userdata.domain;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emailtemplatecategorys")
@Controller
@RooWebScaffold(path = "emailtemplatecategorys", formBackingObject = EmailTemplateCategory.class)
public class EmailTemplateCategoryController {
}
