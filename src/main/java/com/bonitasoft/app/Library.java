package com.bonitasoft.app;

import java.io.*;
import java.util.*;
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
    public static Properties load(String filename) {
        try {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream(filename);
            try{
                properties.load(input);
                return properties;
            } finally{
                input.close();
            }
        } catch (Exception e) {
            traceExeption(e);
            return null;
        }
    }

    public static void initMessage(){
        try{
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
        } catch (Exception e) {
            logger.severe("Error : "+e.getMessage());
        }
    }

    public static String message(String msg, Boolean callback, Boolean inLog) {
        try {
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
        } catch (Exception e) {
            logger.severe("Error : "+e.getMessage());
            return null;
        }
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
            traceExeption(e);
            return null;
        } catch (IOException e) {
            traceExeption(e);
            return null;
        }
    }

    public static List<Map<String,String>> getArgs(String[] args) {
        try {
            List arguments = new ArrayList<Map<String,String>>();

            for(int i = 0; i < args.length; i++) {
                String[] tab = args[i].split("=");
                Map<String, String> arg = new HashMap<String, String>();
                arg.put(tab[0],tab[1]);
                arguments.add(arg);
            }

            if(arguments.size()>0){
                message("+List of arguments : ", false, true);
                for(int i = 0; i < arguments.size(); i++) {
                    Map<String, String> arg = (Map<String, String>) arguments.get(i);
                    for (Map.Entry<String, String> entry : arg.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                }
            }

            return arguments;
        } catch (Exception e) {
            traceExeption(e);
            return null;
        }
    }

    public static String getArgumentValue(List<Map<String,String>> arguments, String key) {
        try {
            String value = "";

            if(arguments.size()>0){
                for(int i = 0; i < arguments.size(); i++) {
                    Map<String, String> arg = (Map<String, String>) arguments.get(i);
                    for (Map.Entry<String, String> entry : arg.entrySet()) {
                        if(entry.getKey().equals(key)){
                            return entry.getValue();
                        }
                    }
                }
            }

            return value;
        } catch (Exception e) {
            traceExeption(e);
            return null;
        }
    }

    public static void traceExeption(Throwable aThrowable){
        String methodeName = Thread.currentThread().getStackTrace()[2].getMethodName();
        message("Error ("+methodeName+") : "+ getStackTrace(aThrowable), false, true);
    }

    public static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
