package com.rickdane.springmodularizedproject.module.consumabledata.web;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.EmailTransport;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.ReceivedEmail;

import flexjson.JSONDeserializer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RooWebJson(jsonObject = ReceivedEmail.class)
@Controller
@RequestMapping("/receivedemails")
@RooWebScaffold(path = "receivedemails", formBackingObject = ReceivedEmail.class)
public class ReceivedEmailController {

    @RequestMapping(value = "uploadNewRetrievedEmails", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> postEmailMessages(@RequestBody String json) {
        List<ReceivedEmail> receivedEmailList = null;
        try {
            receivedEmailList = new JSONDeserializer<List<ReceivedEmail>>().use("values", ReceivedEmail.class).deserialize(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ReceivedEmail email : receivedEmailList) {
            email.persist();
        }
        String jsonStr = "";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(jsonStr, headers, HttpStatus.CREATED);
    }
}
