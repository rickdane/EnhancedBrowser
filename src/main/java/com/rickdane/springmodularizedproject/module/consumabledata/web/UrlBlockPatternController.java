package com.rickdane.springmodularizedproject.module.consumabledata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.UrlBlockPattern;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.UrlBlockPattern;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/urlblockpatterns")
@Controller
@RooWebScaffold(path = "urlblockpatterns", formBackingObject = UrlBlockPattern.class)
public class UrlBlockPatternController {
}
