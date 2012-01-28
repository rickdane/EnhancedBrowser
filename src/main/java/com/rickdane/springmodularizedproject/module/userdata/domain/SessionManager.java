package com.rickdane.springmodularizedproject.module.userdata.domain;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 1/27/12
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionManager {


    public static SessionValues getSessionAttribute(HttpSession session) {

        SessionValues sessionValues = (SessionValues) session.getAttribute(SessionValues.getSessionObjectKey());

        if (sessionValues == null) {
            sessionValues = new SessionValues();
            session.setAttribute(SessionValues.getSessionObjectKey(), sessionValues);
        }

        return sessionValues;
    }

}
