package com.rickdane.springmodularizedproject.module.userdata.domain;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emailtemplatesubjects")
@Controller
@RooWebScaffold(path = "emailtemplatesubjects", formBackingObject = EmailTemplateSubject.class)
public class EmailTemplateSubjectController {
}
