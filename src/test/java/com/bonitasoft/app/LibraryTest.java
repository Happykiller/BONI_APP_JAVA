package com.bonitasoft.app;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

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
}
