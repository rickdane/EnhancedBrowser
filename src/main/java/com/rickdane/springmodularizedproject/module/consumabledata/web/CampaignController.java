package com.rickdane.springmodularizedproject.module.consumabledata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.CampaignEmailScrapeOptions;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Datarecord;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;
import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateCategory;

import java.util.Arrays;

import com.rickdane.springmodularizedproject.module.userdata.domain.SessionManager;
import com.rickdane.springmodularizedproject.module.userdata.domain.SessionValues;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/campaigns")
@Controller
@RooWebScaffold(path = "campaigns", formBackingObject = Campaign.class)
@RooWebJson(jsonObject = Campaign.class)
public class CampaignController {


    @RequestMapping(value = "/selectForSession", produces = "text/html")
    public String selectList(Campaign campaign, Model uiModel) {
        uiModel.addAttribute("campaign", campaign);
        uiModel.addAttribute("campaigns", Campaign.findAllCampaigns());

        return "campaigns/selectForSession";
    }

    @RequestMapping(value = "/select/{id}", produces = "text/html")
    public String sessionSelect(@PathVariable("id") Long id, Model uiModel, HttpSession session, HttpServletRequest httpServletRequest) {

        //TODO: ensure that user is allowed to access this campaign

        SessionValues sessionValues = SessionManager.getSessionAttribute(session);

        Campaign campaign = Campaign.findCampaign(id);

        sessionValues.setCurrentCampaign(campaign);
        System.out.println("current session campaign set");

        return "token";
    }

    void populateEditForm(Model uiModel, Campaign campaign) {
        uiModel.addAttribute("campaign", campaign);
        uiModel.addAttribute("campaignemailscrapeoptionses", Arrays.asList(CampaignEmailScrapeOptions.values()));
        uiModel.addAttribute("datarecords", Datarecord.findAllDatarecords());
        uiModel.addAttribute("emailTemplateCategories", EmailTemplateCategory.findAllEmailTemplateCategorys());
    }
}
