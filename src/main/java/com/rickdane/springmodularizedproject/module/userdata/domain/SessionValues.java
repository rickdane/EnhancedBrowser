package com.rickdane.springmodularizedproject.module.userdata.domain;

import com.rickdane.springmodularizedproject.domain.User;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Campaign;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Url;
import com.rickdane.springmodularizedproject.module.consumabledata.domain.Website;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/15/12
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionValues {

    public static String sessionObjectKey = "sessionValues";

    private Website currentWebsite;

    private User currentuser;

    private User selectedUser;

    private Campaign currentCampaign;

    private Url selectedUrl;

    public static String getSessionObjectKey() {
        return sessionObjectKey;
    }

    public static void setSessionObjectKey(String sessionObjectKey) {
        SessionValues.sessionObjectKey = sessionObjectKey;
    }

    public Url getSelectedUrl() {
        return selectedUrl;
    }

    public void setSelectedUrl(Url selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public User getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Campaign getCurrentCampaign() {
        return currentCampaign;
    }

    public void setCurrentCampaign(Campaign currentCampaign) {
        this.currentCampaign = currentCampaign;
    }

    public Website getCurrentWebsite() {
        return currentWebsite;
    }

    public void setCurrentWebsite(Website currentWebsite) {
        this.currentWebsite = currentWebsite;
    }
}
