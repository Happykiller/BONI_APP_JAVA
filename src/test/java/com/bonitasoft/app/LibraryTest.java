package com.bonitasoft.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Library Tester.
 *
 * @author <Authors name>
 * @since <pre>nov. 3, 2015</pre>
 * @version 1.0
 */
public class LibraryTest {
    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {

    }

    /**
     * Method: load(String filename)
     */
    @Test
    public void testLoad() throws Exception {
        Properties prop = Library.load("BONI_APP_JAVA.properties");
        Assert.assertEquals(prop.getProperty("bonitaBPM.applicationName"),"bonita");
    }

    /**
     * Method: getArgs(String[] args)
     */
    @Test
    public void testGetArgs() throws Exception {
        List<Map<String,String>> waiting = new ArrayList<Map<String, String>>();
        Map<String, String> arg = new HashMap<String, String>();
        arg.put("mode", "full");
        waiting.add(arg);

        String[] args = {"mode=full"};

        List<Map<String,String>> receive = Library.getArgs(args);

        Assert.assertEquals(waiting, receive);
    }

    /**
     * Method: getArgumentValue(List<Map<String,String>> arguments, String key)
     */
    @Test
    public void testGetArgumentValue() throws Exception {
        String waiting = "full";

        List<Map<String,String>> arguments = new ArrayList<Map<String, String>>();
        Map<String, String> arg = new HashMap<String, String>();
        arg.put("mode", "full");
        arguments.add(arg);

        String receive = Library.getArgumentValue(arguments, "mode");

        Assert.assertEquals(waiting, receive);
    }
}
