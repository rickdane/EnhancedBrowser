package com.rickdane.springmodularizedproject.module.consumabledata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.EmailTransport;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Emailaddress;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.WebsiteEmailSendStatus;
import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateBody;
import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateCategory;
import com.rickdane.springmodularizedproject.module.userdata.domain.EmailTemplateSubject;
import com.rickdane.utility.RandomSelector;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/emailaddresses")
@Controller
@RooWebScaffold(path = "emailaddresses", formBackingObject = Emailaddress.class)
@RooWebJson(jsonObject = Emailaddress.class)
public class EmailaddressController {

    @RequestMapping(params = "findEmailaddressesByWebsite", produces = "text/html")
    public String findByWebsite(Model uiModel) {
        populateEditForm(uiModel, new Emailaddress());
        return "emailaddresses/findEmailaddressesByWebsite";
    }

    @RequestMapping(value = "getEmailToSend", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> getEmailToSend(@RequestBody String json) {
        EmailTransport emailTransport = EmailTransport.getPreparedEmailTransport();
        String jsonStr = emailTransport.toJson();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(jsonStr, headers, HttpStatus.CREATED);
    }
}
