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
import java.util.*;

/**
 * Created by HAPPYBONITA on 28/09/2015.
 */
public class Main {

    private static Properties prop;

    private static List<Map<String, String>> arguments;

    public static void main(String [ ] args){
        // init message system
        Library.initMessage();

        try {
            // load properties
            prop = Library.load("BONI_APP_JAVA.properties");
            if(prop == null){
                throw new IllegalArgumentException("No properties found.");
            }

            // start message
            Library.message("===== "+prop.getProperty("app.name")+" - START ======", false, true);

            // loading args
            arguments = Library.getArgs(args);
            if(arguments == null){
                throw new IllegalArgumentException("No arguments detected, "+prop.getProperty("app.name")+" stop.");
            }

            // swich mopde
            String s = Library.getArgumentValue(arguments, "mode");
            if (s.equals("full")) {
                testConnection();
            } else {
                throw new IllegalArgumentException("Invalid argument of mode");
            }

            // end message
            Library.message("===== "+prop.getProperty("app.name")+" - END ======", false, true);
        }catch (Exception e){
            Library.traceExeption(e);
        }
    }

    /**
     *
     */
    public static void testConnection(){
        try{
            // start message
            Library.message("+testConnection:start", false, true);

            // Setup access type (HTTP on local host)
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("server.url", prop.getProperty("bonitaBPM.serverUrl"));
            parameters.put("application.name", prop.getProperty("bonitaBPM.applicationName"));
            APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, parameters);

            // Authenticate and obtain API session
            LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
            APISession session = loginAPI.login(prop.getProperty("bonitaBPM.techUserLog"), prop.getProperty("bonitaBPM.techUserPass"));

            // Operation
            Long id = TenantAPIAccessor.getIdentityAPI(session).getUserByUserName("walter.bates").getId();
            Library.message("Walter.bates id:"+id.toString(), false, true);

            // logout
            loginAPI.logout(session);

            // end message
            Library.message("+testConnection:end", false, true);
        }catch (Exception e){
            Library.traceExeption(e);
        }
    }
}
