package com.bonitasoft.app;

import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.identity.UserNotFoundException;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.platform.LogoutException;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.session.SessionNotFoundException;
import org.bonitasoft.engine.util.APITypeManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by HAPPYBONITA on 28/09/2015.
 */
public class Main {

    public static void main(String [ ] args){
        // chargement des propriétés
        Properties prop = null;
        try {
            prop = Library.load("BONI_APP_JAVA.properties");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Library.initMessage();

        Library.message("===== "+prop.getProperty("app.name")+" - START ======", false, true);

        String hello = Library.message("Say hello : ", true, true);

        Library.message("hello : "+hello, false, true);

        // Setup access type (HTTP on local host)
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("server.url", prop.getProperty("bonitaBPM.serverUrl"));
        parameters.put("application.name", prop.getProperty("bonitaBPM.applicationName"));
        APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, parameters);
        // Authenticate and obtain API session
        LoginAPI loginAPI = null;
        try {
            loginAPI = TenantAPIAccessor.getLoginAPI();
        } catch (BonitaHomeNotSetException e) {
            e.printStackTrace();
        } catch (ServerAPIException e) {
            e.printStackTrace();
        } catch (UnknownAPITypeException e) {
            e.printStackTrace();
        }
        APISession session = null;
        try {
            session = loginAPI.login(prop.getProperty("bonitaBPM.techUserLog"), prop.getProperty("bonitaBPM.techUserPass"));
        } catch (LoginException e) {
            e.printStackTrace();
        }
        // TODO: Call the API
        try {
            Long id = TenantAPIAccessor.getIdentityAPI(session).getUserByUserName("walter.bates").getId();
            System.out.println("Id:"+id.toString());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (BonitaHomeNotSetException e) {
            e.printStackTrace();
        } catch (ServerAPIException e) {
            e.printStackTrace();
        } catch (UnknownAPITypeException e) {
            e.printStackTrace();
        }
        try {
            loginAPI.logout(session);
        } catch (SessionNotFoundException e) {
            e.printStackTrace();
        } catch (LogoutException e) {
            e.printStackTrace();
        }

        Library.message("===== "+prop.getProperty("app.name")+" - END ======", false, true);
    }
}