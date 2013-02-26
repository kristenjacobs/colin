package jcolin.utils;

import java.io.File;

public class ScriptUtils {

    public static String locate(String envVar, String scriptName) {
        // To locate the given script, we check the passed 
        // environment variable. If this is defined, just use this. 
        // Otherwise, search in the current directory...
        String envPathString = System.getenv(envVar);
        if (envPathString != null) {
            for (String path : envPathString.split(":")) {
                File script = new File(path + File.separator + scriptName);
                if (script.exists()) {                  
                    return script.getAbsolutePath();
                }
            }
            
        } else {
            File script = new File(scriptName);
            if (script.exists()) {
                return script.getAbsolutePath();
            }           
        }
        return null;    
    }
}
