package com.rickdane.springmodularizedproject.module.webgatherer.web;

import com.rickdane.springmodularizedproject.domain.User;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.ProcessStatus;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Scraper;
import java.util.Arrays;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Scraper.class)
@Controller
@RequestMapping("/scrapers")
@RooWebScaffold(path = "scrapers", formBackingObject = Scraper.class)
public class ScraperController {

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Scraper());
        return "scrapers/create";
    }

    void populateEditForm(Model uiModel, Scraper scraper) {
        uiModel.addAttribute("scraper", scraper);
        uiModel.addAttribute("users", User.findAllUsers());
        uiModel.addAttribute("campaigns", Campaign.findAllCampaigns());
        uiModel.addAttribute("processstatuses", Arrays.asList(ProcessStatus.values()));
        uiModel.addAttribute("types", Arrays.asList(Scraper.Type.values()));
    }
}
