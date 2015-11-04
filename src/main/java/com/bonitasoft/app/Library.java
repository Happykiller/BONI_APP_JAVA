package com.bonitasoft.app;

import java.io.*;
import java.util.Properties;
import java.util.logging.*;

/**
 * Created by HAPPYBONITA on 03/11/2015.
 */
public class Library {

    protected static Logger logger = Logger.getLogger("com.bonitaSoft.app");

    /**
     * Charge la liste des propriétés contenu dans le fichier spécifié
     *
     * @param filename le fichier contenant les propriétés
     * @return un objet Properties contenant les propriétés du fichier
     */
    public static Properties load(String filename) throws IOException, FileNotFoundException {
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(filename);
        try{
            properties.load(input);
            return properties;
        } finally{
            input.close();
        }
    }

    public static void initMessage(){
        File theDir = new File("logs");

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                //handle it
            }
        }

        try {
            Handler fh = new FileHandler("logs/trace.%g.log", 9000000, 4, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);
    }

    public static String message(String msg, Boolean callback, Boolean inLog) {
        String receive = null;

        System.out.println(msg);

        if (callback) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                receive = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (inLog) {
            logger.info(msg);
        }

        return receive;
    }

    private static String readFile(String file) {
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            message("Error : "+e.getMessage(), false, true);
            return null;
        } catch (IOException e) {
            message("Error : "+e.getMessage(), false, true);
            return null;
        }
    }
}
