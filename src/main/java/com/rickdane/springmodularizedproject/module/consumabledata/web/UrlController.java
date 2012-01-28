package com.rickdane.springmodularizedproject.module.consumabledata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Url;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.UrlStatus;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.UrlType;
import com.rickdane.springmodularizedproject.module.userdata.domain.SessionManager;
import com.rickdane.springmodularizedproject.module.userdata.domain.SessionValues;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/urls")
@Controller
@RooWebScaffold(path = "urls", formBackingObject = Url.class)
public class UrlController {


    @RequestMapping(value = "/findByStatus/{urlType}/{urlStatus}", produces = "text/html")
    public String findByStatus(@PathVariable("urlType") String urlTypeStr, @PathVariable("urlStatus") String urlStatusStr, Model uiModel, HttpSession session) {

        //it is assumed at this point that a session campaign and session user has been set, before this controller has been called

        UrlType urlType = UrlType.valueOf(urlTypeStr);

        UrlStatus urlStatus = UrlStatus.valueOf(urlStatusStr);

        SessionValues sessionValues = SessionManager.getSessionAttribute(session);

        Campaign campaign = sessionValues.getCurrentCampaign();

        //TODO add in check for user, perms, etc

        if (campaign != null) {

            List <Url> urlList = Url.findUrlsByUrlStatusAndCampaign(urlStatus,campaign).getResultList();
            uiModel.addAttribute("urls", urlList);

            addDateTimeFormatPatterns(uiModel);
        }


        return "urls/list";
    }
    
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("url_datelastpostedto_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }


}
