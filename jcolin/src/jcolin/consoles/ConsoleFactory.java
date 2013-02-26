package jcolin.consoles;

import java.io.IOException;

public class ConsoleFactory {
    public static Console buildConsole() {
        
        String consoleProperty = System.getProperty("Console");
        if ((consoleProperty != null) && (consoleProperty.equals("Basic"))) {
            return new BasicConsole();
        }

        try {
            return new JLineConsole();
            
        } catch (IOException e) {
            return new BasicConsole();
        }
    }
}
