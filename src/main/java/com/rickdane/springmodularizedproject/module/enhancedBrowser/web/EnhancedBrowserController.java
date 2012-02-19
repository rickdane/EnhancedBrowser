package com.rickdane.springmodularizedproject.module.enhancedBrowser.web;

import com.rickdane.springmodularizedproject.module.webgatherer.domain.ProcessStatus;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Scraper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.TypedQuery;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/23/12
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */


@Controller
@RequestMapping("/enhancedbrowser")
public class EnhancedBrowserController {

    @RequestMapping(produces = "text/html")
    public String show(Model uiModel) {

        return "enhancedBrowser/index";
    }


    @RequestMapping(value = "/loadMainMenu", produces = "text/html")
    public String loadMainMenu(Model uiModel) {

        return "mainMenu/index";
    }
}
