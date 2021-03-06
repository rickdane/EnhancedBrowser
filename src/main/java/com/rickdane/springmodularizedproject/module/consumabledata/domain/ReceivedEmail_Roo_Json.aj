// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.ReceivedEmail;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ReceivedEmail_Roo_Json {
    
    public String ReceivedEmail.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ReceivedEmail ReceivedEmail.fromJsonToReceivedEmail(String json) {
        return new JSONDeserializer<ReceivedEmail>().use(null, ReceivedEmail.class).deserialize(json);
    }
    
    public static String ReceivedEmail.toJsonArray(Collection<ReceivedEmail> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ReceivedEmail> ReceivedEmail.fromJsonArrayToReceivedEmails(String json) {
        return new JSONDeserializer<List<ReceivedEmail>>().use(null, ArrayList.class).use("values", ReceivedEmail.class).deserialize(json);
    }
    
}
