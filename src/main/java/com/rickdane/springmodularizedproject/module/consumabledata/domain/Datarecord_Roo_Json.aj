// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.rickdane.springmodularizedproject.module.consumabledata.domain;

import com.rickdane.springmodularizedproject.module.consumabledata.domain.Datarecord;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Datarecord_Roo_Json {
    
    public String Datarecord.toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }
    
    public static Datarecord Datarecord.fromJsonToDatarecord(String json) {
        return new JSONDeserializer<Datarecord>().use(null, Datarecord.class).deserialize(json);
    }
    
    public static String Datarecord.toJsonArray(Collection<Datarecord> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }
    
    public static Collection<Datarecord> Datarecord.fromJsonArrayToDatarecords(String json) {
        return new JSONDeserializer<List<Datarecord>>().use(null, ArrayList.class).use("values", Datarecord.class).deserialize(json);
    }
    
}