package com.rickdane.springmodularizedproject.module.userdata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;
import com.rickdane.springmodularizedproject.module.userdata.domain.SessionValues;
import com.rickdane.springmodularizedproject.module.webgatherer.domain.Rawscrapeddata;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/15/12
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */

@RequestMapping("/sessionvalues")
@Controller
public class SessionValueController {

    //value = "/{parameterName}/{id}",
    //@PathVariable("parameterName") String parameterName, @PathVariable("value")

    @RequestMapping(value = "/{parameterName}/{id}", produces = "text/html")
    public String setSessionVariable(@PathVariable("parameterName") String parameterName, @PathVariable("id") Long id, Model uiModel, HttpSession session) {

        SessionValues sessionValues = (SessionValues) session.getAttribute(SessionValues.sessionObjectKey);

        if (sessionValues == null) {
            sessionValues = new SessionValues();
        }

        //TODO figure a better way to do this
        if (parameterName.equals("website")) {
            Website website = Website.findWebsite(id);
            sessionValues.setCurrentWebsite(website);
            session.setAttribute(SessionValues.sessionObjectKey, sessionValues);

            uiModel.addAttribute("token","Currently selected website is: " + website.getDomainName());
        }

        return "token";
    }

}
